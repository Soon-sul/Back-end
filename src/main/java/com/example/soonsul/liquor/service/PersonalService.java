package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.PersonalDto;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Location;
import com.example.soonsul.liquor.entity.LocationInfo;
import com.example.soonsul.liquor.entity.Review;
import com.example.soonsul.liquor.exception.CodeNotExist;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.exception.LocationInfoNotExist;
import com.example.soonsul.liquor.exception.ReviewNotExist;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.PersonalEvaluation;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalService {
    private final LiquorRepository liquorRepository;
    private final PersonalEvaluationRepository personalEvaluationRepository;
    private final UserUtil userUtil;
    private final CodeRepository codeRepository;
    private final LocationRepository locationRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final ReviewRepository reviewRepository;


    @Transactional(readOnly = true)
    public List<PersonalDto> getPersonalEvaluationList(Pageable pageable){
        final User user= userUtil.getUserByAuthentication();
        final List<PersonalEvaluation> list= personalEvaluationRepository.findAll(pageable, user.getUserId()).toList();

        final List<PersonalDto> result= new ArrayList<>();
        for(PersonalEvaluation p: list){
            final Liquor liquor= p.getLiquor();
            final String liquorCategory= codeRepository.findById(liquor.getLiquorCategory())
                    .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();

            final List<Location> locations = locationRepository.findAllByLiquor(liquor);
            final List<String> locationList = new ArrayList<>();
            for (Location l : locations) {
                final LocationInfo info = locationInfoRepository.findById(l.getLocationInfoId())
                        .orElseThrow(() -> new LocationInfoNotExist("location info not exist", ErrorCode.LOCATION_INFO_NOT_EXIST));
                locationList.add(info.getBrewery());
            }

            final Optional<Review> review= reviewRepository.findByUserAndLiquor(user, liquor);

            final PersonalDto dto= PersonalDto.builder()
                    .liquorId(liquor.getLiquorId())
                    .name(liquor.getName())
                    .averageRating(liquor.getAverageRating())
                    .imageUrl(liquor.getImageUrl())
                    .liquorCategory(liquorCategory)
                    .locationList(locationList)
                    .evaluationDate(p.getEvaluationDate())
                    .personalEvaluationId(p.getPersonalEvaluationId())
                    .personalRating(p.getLiquorPersonalRating())
                    .reviewId(review.map(Review::getReviewId).orElse(null))
                    .reviewContent(review.map(Review::getContent).orElse(null))
                    .sweetness(p.getSweetness())
                    .acidity(p.getAcidity())
                    .carbonicAcid(p.getCarbonicAcid())
                    .heavy(p.getHeavy())
                    .scent(p.getScent())
                    .density(p.getDensity())
                    .build();
            result.add(dto);
        }
        return result;
    }

}
