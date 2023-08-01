package com.example.soonsul.scan;

import com.example.soonsul.config.s3.S3Uploader;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Location;
import com.example.soonsul.liquor.entity.LocationInfo;
import com.example.soonsul.liquor.exception.CodeNotExist;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.exception.LocationInfoNotExist;
import com.example.soonsul.liquor.repository.CodeRepository;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.liquor.repository.LocationInfoRepository;
import com.example.soonsul.liquor.repository.LocationRepository;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.scan.dto.ScanDto;
import com.example.soonsul.scan.exception.ScanNotExist;
import com.example.soonsul.user.entity.PersonalEvaluation;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.soonsul.response.error.ErrorCode.SCAN_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class ScanService {
    private final ScanRepository scanRepository;
    private final LiquorRepository liquorRepository;
    private final UserUtil userUtil;
    private final S3Uploader s3Uploader;
    private final CodeRepository codeRepository;
    private final LocationRepository locationRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final PersonalEvaluationRepository personalEvaluationRepository;


    @Value("${cloud.aws.s3.bucket.url}")
    private String AWS_S3_BUCKET_URL;


    @Transactional(readOnly = true)
    public String getLiquor(String name){
        final Optional<Liquor> liquor= liquorRepository.findByName(name);
        return (liquor.isPresent()) ? liquor.get().getLiquorId() : "";
    }


    @Transactional
    public void postScan(String liquorId, MultipartFile image){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final Scan scan= Scan.builder()
                .scanDate(LocalDate.now())
                .imageUrl(s3Uploader.upload(image,"scan"))
                .user(user)
                .liquor(liquor)
                .build();
        scanRepository.save(scan);
    }


    @Transactional(readOnly = true)
    public List<ScanDto> getScanList(Pageable pageable){
        final User user= userUtil.getUserByAuthentication();
        final List<Scan> scanList= scanRepository.findAllByUser(pageable, user.getUserId()).toList();

        final List<ScanDto> result= new ArrayList<>();
        for(Scan s: scanList){
            final Liquor liquor= s.getLiquor();

            final String liquorCategory= codeRepository.findById(liquor.getLiquorCategory())
                    .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();

            final List<Location> locations = locationRepository.findAllByLiquor(liquor);
            final List<String> locationList = new ArrayList<>();
            for (Location l : locations) {
                final LocationInfo info = locationInfoRepository.findById(l.getLocationInfoId())
                        .orElseThrow(() -> new LocationInfoNotExist("location info not exist", ErrorCode.LOCATION_INFO_NOT_EXIST));
                locationList.add(info.getBrewery());
            }

            boolean flag= true;
            final Optional<PersonalEvaluation> evaluation= personalEvaluationRepository.findByUserAndLiquor(user, liquor);
            if(evaluation.isEmpty()) flag=false;

            final ScanDto dto= ScanDto.builder()
                    .scanId(s.getScanId())
                    .scanDate(s.getScanDate())
                    .imageUrl(s.getImageUrl())
                    .liquorId(liquor.getLiquorId())
                    .name(liquor.getName())
                    .liquorCategory(liquorCategory)
                    .locationList(locationList)
                    .averageRating(liquor.getAverageRating())
                    .flagEvaluation(flag)
                    .personalRating((flag) ? evaluation.get().getLiquorPersonalRating() : null)
                    .build();
            result.add(dto);
        }
        return result;
    }


    @Transactional
    public void deleteScan(Long scanId){
        final Scan scan= scanRepository.findById(scanId)
                .orElseThrow(()-> new ScanNotExist("scan not exist", ErrorCode.SCAN_NOT_EXIST));
        s3Uploader.deleteFile(scan.getImageUrl().substring(AWS_S3_BUCKET_URL.length()));
        scanRepository.deleteById(scanId);
    }

}
