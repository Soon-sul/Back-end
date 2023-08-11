package com.example.soonsul.util;

import com.example.soonsul.liquor.entity.Evaluation;
import com.example.soonsul.liquor.entity.EvaluationNumber;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.exception.EvaluationNotExist;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.repository.EvaluationNumberRepository;
import com.example.soonsul.liquor.repository.EvaluationRepository;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.liquor.repository.ReviewRepository;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.PersonalEvaluation;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.exception.PersonalEvaluationNotExist;
import com.example.soonsul.user.exception.UserNotExist;
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiquorUtil {
    private final LiquorRepository liquorRepository;
    private final PersonalEvaluationRepository personalEvaluationRepository;
    private final EvaluationRepository evaluationRepository;
    private final ReviewRepository reviewRepository;
    private final EvaluationNumberRepository evaluationNumberRepository;


    public Liquor getLiquor(String liquorId){
        return liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
    }

    public Evaluation getEvaluation(String liquorId){
        return evaluationRepository.findById(liquorId)
                .orElseThrow(()-> new EvaluationNotExist("evaluation not exist", ErrorCode.EVALUATION_NOT_EXIST));
    }

    public EvaluationNumber getEvaluationNumber(String liquorId){
        return evaluationNumberRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("evaluation number not exist", ErrorCode.LIQUOR_NOT_EXIST));
    }

    public PersonalEvaluation getPersonalEvaluation(User user, Liquor liquor){
        return personalEvaluationRepository.findByUserAndLiquor(user, liquor)
                .orElseThrow(()-> new PersonalEvaluationNotExist("liquor evaluation not exist", ErrorCode.PERSONAL_EVALUATION_NOT_EXIST));
    }
}
