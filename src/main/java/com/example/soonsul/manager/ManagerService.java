package com.example.soonsul.manager;

import com.example.soonsul.config.s3.S3Uploader;
import com.example.soonsul.liquor.entity.Evaluation;
import com.example.soonsul.liquor.entity.EvaluationNumber;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.repository.EvaluationNumberRepository;
import com.example.soonsul.liquor.repository.EvaluationRepository;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.response.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final S3Uploader s3Uploader;
    private final LiquorRepository liquorRepository;
    private final EvaluationRepository evaluationRepository;
    private final EvaluationNumberRepository numberRepository;


    @Transactional
    public void postMainPhoto(List<MultipartFile> images){
        for(MultipartFile image: images){
            final String liquorId= image.getOriginalFilename().substring(0,8);
            final Liquor liquor= liquorRepository.findById(liquorId)
                    .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
            liquor.updateImageUrl(s3Uploader.liquorMainUpload(image));
        }
    }


    @Transactional
    public void postInit(){
        final List<Liquor> list= liquorRepository.findAll();

        for(Liquor liquor: list){
            final Optional<Evaluation> e= evaluationRepository.findById(liquor.getLiquorId());
            if(e.isPresent()) continue;

            final Evaluation evaluation= Evaluation.builder()
                    .evaluationId(liquor.getLiquorId())
                    .sweetness(0.0)
                    .acidity(0.0)
                    .carbonicAcid(0.0)
                    .heavy(0.0)
                    .scent(0.0)
                    .density(0.0)
                    .build();
            evaluationRepository.save(evaluation);

            final EvaluationNumber number= EvaluationNumber.builder()
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


}
