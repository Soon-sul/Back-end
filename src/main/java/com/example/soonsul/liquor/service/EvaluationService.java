package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.EvaluationRequest;
import com.example.soonsul.liquor.entity.*;
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
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final LiquorRepository liquorRepository;
    private final PersonalEvaluationRepository personalEvaluationRepository;
    private final UserUtil userUtil;
    private final EvaluationRepository evaluationRepository;
    private final ReviewRepository reviewRepository;
    private final EvaluationNumberRepository evaluationNumberRepository;


    @Transactional
    public void postEvaluation(String liquorId, EvaluationRequest request){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));

        final PersonalEvaluation personalEvaluation= PersonalEvaluation.builder()
                .liquorPersonalRating(request.getLiquorPersonalRating())
                .sweetness(request.getSweetness())
                .acidity(request.getAcidity())
                .carbonicAcid(request.getCarbonicAcid())
                .heavy(request.getHeavy())
                .scent(request.getScent())
                .density(request.getDensity())
                .evaluationDate(LocalDate.now())
                .user(user)
                .liquor(liquor)
                .build();
        personalEvaluationRepository.save(personalEvaluation);

        calAverageByPost(liquor, request);

        if(request.getReviewContent()!=null){
            final Review review= Review.builder()
                    .content(request.getReviewContent())
                    .good(0)
                    .createdDate(LocalDateTime.now())
                    .liquorRating(request.getLiquorPersonalRating())
                    .user(user)
                    .liquor(liquor)
                    .build();
            reviewRepository.save(review);
        }
    }


    @Transactional
    public void putEvaluation(String liquorId, EvaluationRequest request){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final PersonalEvaluation personalEvaluation= personalEvaluationRepository.findByUserAndLiquor(user, liquor)
                .orElseThrow(()-> new PersonalEvaluationNotExist("liquor evaluation not exist", ErrorCode.PERSONAL_EVALUATION_NOT_EXIST));

        calAverageByPut(liquor, request, personalEvaluation);

        final Optional<Review> review= reviewRepository.findByUserAndLiquor(user, liquor);
        if(request.getReviewContent()==null){
            review.ifPresent(value -> reviewRepository.deleteById(value.getReviewId()));
        }
        else{
            if(!request.getLiquorPersonalRating().equals(review.get().getLiquorRating()))
                review.get().updateLiquorRating(request.getLiquorPersonalRating());
            review.get().updateContent(request.getReviewContent());
        }
    }


    @Transactional
    public void deletePersonalEvaluation(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final Evaluation evaluation= evaluationRepository.findById(liquor.getLiquorId())
                .orElseThrow(()-> new EvaluationNotExist("evaluation not exist", ErrorCode.EVALUATION_NOT_EXIST));
        final EvaluationNumber number= evaluationNumberRepository.findById(liquor.getLiquorId())
                .orElseThrow(()-> new LiquorNotExist("evaluation number not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final PersonalEvaluation personalEvaluation= personalEvaluationRepository.findByUserAndLiquor(user, liquor)
                .orElseThrow(()-> new PersonalEvaluationNotExist("liquor evaluation not exist", ErrorCode.PERSONAL_EVALUATION_NOT_EXIST));

        subAverageRating(liquor, number, personalEvaluation);

        if(personalEvaluation.getSweetness()!=null)
            sub(FlavorType.SWEETNESS, null, evaluation, number, personalEvaluation);
        if(personalEvaluation.getAcidity()!=null)
            sub(FlavorType.ACIDITY, null, evaluation, number, personalEvaluation);
        if(personalEvaluation.getCarbonicAcid()!=null)
            sub(FlavorType.CARBONIC_ACID, null, evaluation, number, personalEvaluation);
        if(personalEvaluation.getHeavy()!=null)
            sub(FlavorType.HEAVY, null, evaluation, number, personalEvaluation);
        if(personalEvaluation.getScent()!=null)
            sub(FlavorType.SCENT, null, evaluation, number, personalEvaluation);
        if(personalEvaluation.getDensity()!=null)
            sub(FlavorType.DENSITY, null, evaluation, number, personalEvaluation);

        personalEvaluationRepository.delete(personalEvaluation);
        if(reviewRepository.findByUserAndLiquor(user, liquor).isPresent())
            reviewRepository.deleteByUserAndLiquor(user, liquor);
    }


    private void calAverageByPost(Liquor liquor, EvaluationRequest request){
        final Evaluation evaluation= evaluationRepository.findById(liquor.getLiquorId())
                .orElseThrow(()-> new EvaluationNotExist("evaluation not exist", ErrorCode.EVALUATION_NOT_EXIST));
        final EvaluationNumber number= evaluationNumberRepository.findById(liquor.getLiquorId())
                .orElseThrow(()-> new LiquorNotExist("evaluation number not exist", ErrorCode.LIQUOR_NOT_EXIST));

        addAverageRating(liquor, number, request.getLiquorPersonalRating());
        if(request.getSweetness()!=null) addSweetness(evaluation, number, request.getSweetness());
        if(request.getAcidity()!=null) addAcidity(evaluation, number, request.getAcidity());
        if(request.getCarbonicAcid()!=null) addCarbonicAcid(evaluation, number, request.getCarbonicAcid());
        if(request.getHeavy()!=null) addHeavy(evaluation, number, request.getHeavy());
        if(request.getScent()!=null) addScent(evaluation, number, request.getScent());
        if(request.getDensity()!=null) addDensity(evaluation, number, request.getDensity());
    }


    private void calAverageByPut(Liquor liquor, EvaluationRequest request, PersonalEvaluation pe){
        final Evaluation evaluation= evaluationRepository.findById(liquor.getLiquorId())
                .orElseThrow(()-> new EvaluationNotExist("evaluation not exist", ErrorCode.EVALUATION_NOT_EXIST));
        final EvaluationNumber number= evaluationNumberRepository.findById(liquor.getLiquorId())
                .orElseThrow(()-> new LiquorNotExist("evaluation number not exist", ErrorCode.LIQUOR_NOT_EXIST));

        if(!pe.getLiquorPersonalRating().equals(request.getLiquorPersonalRating())){  //수정할 평점이 null 아니라는 전제하에
            subAverageRating(liquor, number, pe);
            addAverageRating(liquor, number, request.getLiquorPersonalRating());
            pe.updateLiquorPersonalRating(request.getLiquorPersonalRating());
        }

        if(checkByEqual(pe.getSweetness(),request.getSweetness()))
            calFlavorEvaluation(FlavorType.SWEETNESS, pe.getSweetness(), request.getSweetness(), evaluation, number, pe);

        if(checkByEqual(pe.getAcidity(),request.getAcidity()))
            calFlavorEvaluation(FlavorType.ACIDITY, pe.getAcidity(), request.getAcidity(), evaluation, number, pe);

        if(checkByEqual(pe.getCarbonicAcid(),request.getCarbonicAcid()))
            calFlavorEvaluation(FlavorType.CARBONIC_ACID, pe.getCarbonicAcid(), request.getCarbonicAcid(), evaluation, number, pe);

        if(checkByEqual(pe.getHeavy(),request.getHeavy()))
            calFlavorEvaluation(FlavorType.HEAVY, pe.getHeavy(), request.getHeavy(), evaluation, number, pe);

        if(checkByEqual(pe.getScent(),request.getScent()))
            calFlavorEvaluation(FlavorType.SCENT, pe.getScent(), request.getScent(), evaluation, number, pe);

        if(checkByEqual(pe.getDensity(),request.getDensity()))
            calFlavorEvaluation(FlavorType.DENSITY, pe.getDensity(), request.getDensity(), evaluation, number, pe);

    }

    private boolean checkByNull(Integer origin, Integer request){
        return origin!=null || request!=null;
    }

    private boolean checkByEqual(Integer origin, Integer request){
        if(origin==null) return true;
        return !origin.equals(request);
    }


    private void calFlavorEvaluation(FlavorType flavorType, Integer origin, Integer request, Evaluation evaluation,
                                     EvaluationNumber number, PersonalEvaluation pe){
        if(origin==null && request!=null) add(flavorType, request, evaluation, number, pe);
        else if(origin!=null && request==null) sub(flavorType, request, evaluation, number, pe);
        else if(origin != null) subAndAdd(flavorType, request, evaluation, number, pe);;
    }

    private void add(FlavorType flavorType, Integer request, Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        switch (flavorType){
            case SWEETNESS:
                addSweetness(evaluation, number, request);
                pe.updateSweetness(request);
                break;
            case ACIDITY:
                addAcidity(evaluation, number, request);
                pe.updateAcidity(request);
                break;
            case CARBONIC_ACID:
                addCarbonicAcid(evaluation, number, request);
                pe.updateCarbonicAcid(request);
                break;
            case HEAVY:
                addHeavy(evaluation, number, request);
                pe.updateHeavy(request);
                break;
            case SCENT:
                addScent(evaluation, number, request);
                pe.updateScent(request);
                break;
            case DENSITY:
                addDensity(evaluation, number, request);
                pe.updateDensity(request);
                break;
        }
    }

    private void sub(FlavorType flavorType, Integer request, Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        switch (flavorType){
            case SWEETNESS:
                subSweetness(evaluation, number, pe);
                pe.updateSweetness(request);
                break;
            case ACIDITY:
                subAcidity(evaluation, number, pe);
                pe.updateAcidity(request);
                break;
            case CARBONIC_ACID:
                subCarbonicAcid(evaluation, number, pe);
                pe.updateCarbonicAcid(request);
                break;
            case HEAVY:
                subHeavy(evaluation, number, pe);
                pe.updateHeavy(request);
                break;
            case SCENT:
                subScent(evaluation, number, pe);
                pe.updateScent(request);
                break;
            case DENSITY:
                subDensity(evaluation, number, pe);
                pe.updateDensity(request);
                break;
        }
    }

    private void subAndAdd(FlavorType flavorType, Integer request, Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        switch (flavorType){
            case SWEETNESS:
                addSweetness(evaluation, number, request);
                subSweetness(evaluation, number, pe);
                pe.updateSweetness(request);
                break;
            case ACIDITY:
                addAcidity(evaluation, number, request);
                subAcidity(evaluation, number, pe);
                pe.updateAcidity(request);
                break;
            case CARBONIC_ACID:
                addCarbonicAcid(evaluation, number, request);
                subCarbonicAcid(evaluation, number, pe);
                pe.updateCarbonicAcid(request);
                break;
            case HEAVY:
                addHeavy(evaluation, number, request);
                subHeavy(evaluation, number, pe);
                pe.updateHeavy(request);
                break;
            case SCENT:
                addScent(evaluation, number, request);
                subScent(evaluation, number, pe);
                pe.updateScent(request);
                break;
            case DENSITY:
                addDensity(evaluation, number, request);
                subDensity(evaluation, number, pe);
                pe.updateDensity(request);
                break;
        }
    }


    private void addAverageRating(Liquor liquor, EvaluationNumber number, Double request){
        liquor.updateAverageRating(calAverage(liquor.getAverageRating(), number.getAverageRating(), request, 1));
        number.addAverageRating(1);
    }

    private void subAverageRating(Liquor liquor, EvaluationNumber number, PersonalEvaluation pe){
        liquor.updateAverageRating(calAverage(liquor.getAverageRating(), number.getAverageRating(), pe.getLiquorPersonalRating(), -1));
        number.addAverageRating(-1);
    }

    private void addSweetness(Evaluation evaluation, EvaluationNumber number, Integer request){
        evaluation.updateSweetness(calAverage(evaluation.getSweetness(), number.getSweetness(), request, 1));
        number.addSweetness(1);
    }

    private void subSweetness(Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        evaluation.updateSweetness(calAverage(evaluation.getSweetness(), number.getSweetness(), pe.getSweetness(), -1));
        number.addSweetness(-1);
    }

    private void addAcidity(Evaluation evaluation, EvaluationNumber number, Integer request){
        evaluation.updateAcidity(calAverage(evaluation.getAcidity(), number.getAcidity(), request, 1));
        number.addAcidity(1);
    }

    private void subAcidity(Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        evaluation.updateAcidity(calAverage(evaluation.getAcidity(), number.getAcidity(), pe.getAcidity(), -1));
        number.addAcidity(-1);
    }

    private void addCarbonicAcid(Evaluation evaluation, EvaluationNumber number, Integer request){
        evaluation.updateCarbonicAcid(calAverage(evaluation.getCarbonicAcid(), number.getCarbonicAcid(), request, 1));
        number.addCarbonicAcid(1);
    }

    private void subCarbonicAcid(Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        evaluation.updateCarbonicAcid(calAverage(evaluation.getCarbonicAcid(), number.getCarbonicAcid(), pe.getCarbonicAcid(), -1));
        number.addCarbonicAcid(-1);
    }

    private void addHeavy(Evaluation evaluation, EvaluationNumber number, Integer request){
        evaluation.updateHeavy(calAverage(evaluation.getHeavy(), number.getHeavy(), request, 1));
        number.addHeavy(1);
    }

    private void subHeavy(Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        evaluation.updateHeavy(calAverage(evaluation.getHeavy(), number.getHeavy(), pe.getHeavy(), -1));
        number.addHeavy(-1);
    }

    private void addScent(Evaluation evaluation, EvaluationNumber number, Integer request){
        evaluation.updateScent(calAverage(evaluation.getScent(), number.getScent(), request, 1));
        number.addScent(1);
    }

    private void subScent(Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        evaluation.updateScent(calAverage(evaluation.getScent(), number.getScent(), pe.getScent(), -1));
        number.addScent(-1);
    }

    private void addDensity(Evaluation evaluation, EvaluationNumber number, Integer request){
        evaluation.updateDensity(calAverage(evaluation.getDensity(), number.getDensity(), request, 1));
        number.addDensity(1);
    }

    private void subDensity(Evaluation evaluation, EvaluationNumber number, PersonalEvaluation pe){
        evaluation.updateDensity(calAverage(evaluation.getDensity(), number.getDensity(), pe.getDensity(), -1));
        number.addDensity(-1);
    }


    private Double calAverage(Double origin, Integer number, Double rating, Integer mark){
        double result= ((origin*number)+(mark*rating))/(number+mark);
        return Math.round(result*10)/10.0;
    }

    private Double calAverage(Double origin, Integer number, Integer rating, Integer mark){
        double result= (double) ((origin*number)+(mark*rating))/(number+mark);
        return Math.round(result*10)/10.0;
    }
}
