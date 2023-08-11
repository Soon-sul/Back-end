package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.PersonalDto;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Location;
import com.example.soonsul.liquor.entity.LocationInfo;
import com.example.soonsul.liquor.entity.Review;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.user.entity.PersonalEvaluation;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import com.example.soonsul.util.LiquorUtil;
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
    private final PersonalEvaluationRepository personalEvaluationRepository;
    private final UserUtil userUtil;
    private final LiquorUtil liquorUtil;
    private final LocationRepository locationRepository;
    private final ReviewRepository reviewRepository;


    @Transactional(readOnly = true)
    public List<PersonalDto> getPersonalEvaluationList(Pageable pageable){
        final User user= userUtil.getUserByAuthentication();
        final List<PersonalEvaluation> list= personalEvaluationRepository.findAll(pageable, user.getUserId()).toList();

        final List<PersonalDto> result= new ArrayList<>();
        for(PersonalEvaluation p: list){
            final Liquor liquor= p.getLiquor();
            final String liquorCategory= liquorUtil.getCodeName(liquor.getLiquorCategory());

            final List<Location> locations = locationRepository.findAllByLiquor(liquor);
            final List<String> locationList = new ArrayList<>();
            for (Location l : locations) {
                final LocationInfo info = liquorUtil.getLocationInfo(l.getLocationInfoId());
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
