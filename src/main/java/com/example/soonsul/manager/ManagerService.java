package com.example.soonsul.manager;

import com.example.soonsul.config.s3.S3Uploader;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final S3Uploader s3Uploader;
    private final LiquorRepository liquorRepository;


    @Transactional
    public void postMainPhoto(List<MultipartFile> images){
        for(MultipartFile image: images){
            final String liquorId= image.getOriginalFilename().substring(0,8);
            final Liquor liquor= liquorRepository.findById(liquorId)
                    .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
            liquor.updateImageUrl(s3Uploader.liquorMainUpload(image));
        }
    }


}
