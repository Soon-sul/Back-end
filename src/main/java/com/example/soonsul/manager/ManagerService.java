package com.example.soonsul.manager;

import com.example.soonsul.config.s3.S3Uploader;
import com.example.soonsul.liquor.entity.Evaluation;
import com.example.soonsul.liquor.entity.EvaluationNumber;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.LocationInfo;
import com.example.soonsul.liquor.repository.EvaluationNumberRepository;
import com.example.soonsul.liquor.repository.EvaluationRepository;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.liquor.repository.LocationInfoRepository;
import com.example.soonsul.manager.dto.LocationRes;
import com.example.soonsul.util.LiquorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final S3Uploader s3Uploader;
    private final LiquorUtil liquorUtil;
    private final LiquorRepository liquorRepository;
    private final EvaluationRepository evaluationRepository;
    private final EvaluationNumberRepository numberRepository;
    private final LocationInfoRepository locationInfoRepository;

    @Value("${map.kakao.apiKey}")
    private String apiKey;

    @Value("${map.kakao.apiUrl}")
    private String apiUrl;

    private final RestTemplate restTemplate;


    @Transactional
    public void postMainPhoto(List<MultipartFile> images) {
        for (MultipartFile image : images) {
            final String liquorId = image.getOriginalFilename().substring(0, 8);
            final Liquor liquor = liquorUtil.getLiquor(liquorId);
            //if (liquor.getImageUrl() != null || !liquor.getImageUrl().equals(""))
            //    s3Uploader.deleteFile(liquor.getImageUrl());
            liquor.updateImageUrl(s3Uploader.liquorMainUpload(image));
        }
    }


    @Transactional
    public void postLiquorInit() {
        final List<Liquor> list = liquorRepository.findAll();

        for (Liquor liquor : list) {
            final Optional<Evaluation> e = evaluationRepository.findById(liquor.getLiquorId());
            if (e.isPresent()) continue;

            final Evaluation evaluation = Evaluation.builder()
                    .evaluationId(liquor.getLiquorId())
                    .sweetness(0.0)
                    .acidity(0.0)
                    .carbonicAcid(0.0)
                    .heavy(0.0)
                    .scent(0.0)
                    .density(0.0)
                    .build();
            evaluationRepository.save(evaluation);

            final EvaluationNumber number = EvaluationNumber.builder()
                    .liquorId(liquor.getLiquorId())
                    .averageRating(0)
                    .sweetness(0)
                    .acidity(0)
                    .carbonicAcid(0)
                    .heavy(0)
                    .scent(0)
                    .density(0)
                    .build();
            numberRepository.save(number);
        }
    }


    @Transactional
    public void postLocationInit() {
        final List<LocationInfo> list = locationInfoRepository.findAll();

        for(LocationInfo info: list){
            final String url = apiUrl + "?query=" + info.getName();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiKey);

            final HttpEntity<String> request = new HttpEntity<>(headers);
            LocationRes response= restTemplate.exchange(url, HttpMethod.GET, request, LocationRes.class).getBody();

            info.updateLatitude(response.documents[0].latitude);
            info.updateLongitude(response.documents[0].longitude);
        }
    }

}