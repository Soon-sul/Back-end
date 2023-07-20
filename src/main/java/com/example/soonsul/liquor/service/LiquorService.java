package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.*;
import com.example.soonsul.liquor.entity.*;
import com.example.soonsul.liquor.exception.*;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.PersonalEvaluation;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.exception.PersonalEvaluationNotExist;
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiquorService {
    private final LiquorRepository liquorRepository;
    private final PersonalEvaluationRepository personalEvaluationRepository;
    private final UserUtil userUtil;
    private final PrizeRepository prizeRepository;
    private final EvaluationRepository evaluationRepository;
    private final CodeRepository codeRepository;
    private final LocationRepository locationRepository;
    private final SalePlaceRepository salePlaceRepository;
    private final PrizeInfoRepository prizeInfoRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final SalePlaceInfoRepository salePlaceInfoRepository;
    private final ReviewRepository reviewRepository;


    @Transactional(readOnly = true)
    public LiquorInfoDto getLiquorInfo(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final Optional<PersonalEvaluation> personalEvaluation= personalEvaluationRepository.findByUserAndLiquor(user,liquor);

        Double liquorPersonalRating= null;
        if(personalEvaluation.isPresent()){
            liquorPersonalRating= personalEvaluation.get().getLiquorPersonalRating();
        }

        final String region= codeRepository.findById(liquor.getRegion())
                .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();
        final String liquorCategory= codeRepository.findById(liquor.getLiquorCategory())
                .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();

        return LiquorInfoDto.builder()
                .name(liquor.getName())
                .ingredient(liquor.getIngredient())
                .averageRating(liquor.getAverageRating())
                .lowestPrice(liquor.getLowestPrice())
                .alcohol(liquor.getAlcohol())
                .capacity(liquor.getCapacity())
                .viewCount(liquor.getViewCount())
                .region(region)
                .imageUrl(liquor.getImageUrl())
                .liquorCategory(liquorCategory)
                .liquorPersonalRating(liquorPersonalRating)
                .ratingNumber(reviewRepository.countByLiquor(liquor))
                .build();
    }


    @Transactional(readOnly = true)
    public List<PrizeListDto> getLiquorPrize(String liquorId){
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final List<Prize> prizeList= prizeRepository.findAllByLiquor(liquor);

        final List<PrizeListDto> result= new ArrayList<>();
        for(Prize p: prizeList){
            final PrizeInfo info= prizeInfoRepository.findById(p.getPrizeInfoId())
                    .orElseThrow(()-> new PrizeInfoNotExist("prize info not exist",ErrorCode.PRIZE_INFO_NOT_EXIST));
            final PrizeListDto dto = PrizeListDto.builder()
                    .prizeId(p.getPrizeId())
                    .name(info.getName())
                    .build();
            result.add(dto);
        }
        return result;
    }


    @Transactional(readOnly = true)
    public List<LocationListDto> getLiquorLocation(String liquorId){
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final List<Location> locationList= locationRepository.findAllByLiquor(liquor);

        final List<LocationListDto> result= new ArrayList<>();
        for(Location l: locationList){
            final LocationInfo info= locationInfoRepository.findById(l.getLocationInfoId())
                    .orElseThrow(()-> new LocationInfoNotExist("location info not exist",ErrorCode.LOCATION_INFO_NOT_EXIST));
            final LocationListDto dto = LocationListDto.builder()
                    .locationInfoId(l.getLocationId())
                    .name(info.getName())
                    .latitude(info.getLatitude())
                    .longitude(info.getLongitude())
                    .brewery(info.getBrewery())
                    .build();
            result.add(dto);
        }
        return result;
    }


    @Transactional(readOnly = true)
    public List<SalePlaceListDto> getLiquorSalePlace(String liquorId){
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final List<SalePlace> salePlaceList= salePlaceRepository.findAllByLiquor(liquor);

        final List<SalePlaceListDto> result= new ArrayList<>();
        for(SalePlace s: salePlaceList){
            final SalePlaceInfo info= salePlaceInfoRepository.findById(s.getSalePlaceInfoId())
                    .orElseThrow(()-> new SalePlaceInfoNotExist("sale place info not exist",ErrorCode.SALE_PLACE_INFO_NOT_EXIST));
            final SalePlaceListDto dto = SalePlaceListDto.builder()
                    .salePlaceId(s.getSalePlaceId())
                    .name(info.getName())
                    .phoneNumber(info.getPhoneNumber())
                    .siteUrl(info.getSiteUrl())
                    .build();
            result.add(dto);
        }
        return result;
    }


    @Transactional(readOnly = true)
    public boolean getEvaluationCheck(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final Optional<PersonalEvaluation> evaluation= personalEvaluationRepository.findByUserAndLiquor(user, liquor);

        return evaluation.isPresent();
    }


    @Transactional(readOnly = true)
    public EvaluationDto getFlavorAverage(String liquorId){
        final Evaluation evaluation= evaluationRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor evaluation not exist", ErrorCode.LIQUOR_NOT_EXIST));

        return EvaluationDto.builder()
                .sweetness(evaluation.getSweetness())
                .acidity(evaluation.getAcidity())
                .carbonicAcid(evaluation.getCarbonicAcid())
                .heavy(evaluation.getHeavy())
                .scent(evaluation.getScent())
                .density(evaluation.getDensity())
                .build();
    }


    @Transactional(readOnly = true)
    public PersonEvaluationDto getFlavorPerson(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final PersonalEvaluation evaluation= personalEvaluationRepository.findByUserAndLiquor(user, liquor)
                .orElseThrow(()-> new LiquorNotExist("liquor evaluation not exist", ErrorCode.LIQUOR_NOT_EXIST));

        return PersonEvaluationDto.builder()
                .sweetness(evaluation.getSweetness())
                .acidity(evaluation.getAcidity())
                .carbonicAcid(evaluation.getCarbonicAcid())
                .heavy(evaluation.getHeavy())
                .scent(evaluation.getScent())
                .density(evaluation.getDensity())
                .build();
    }


    @Transactional(readOnly = true)
    public boolean getFlavorCheck(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final PersonalEvaluation evaluation= personalEvaluationRepository.findByUserAndLiquor(user, liquor)
                .orElseThrow(()-> new PersonalEvaluationNotExist("liquor evaluation not exist", ErrorCode.PERSONAL_EVALUATION_NOT_EXIST));

        boolean flag= false;
        if(evaluation.getSweetness()!=null) flag= true;
        else if(evaluation.getAcidity()!=null) flag= true;
        else if(evaluation.getCarbonicAcid()!=null) flag= true;
        else if(evaluation.getHeavy()!=null) flag= true;
        else if(evaluation.getScent()!=null) flag= true;
        else if(evaluation.getDensity()!=null) flag= true;

        return flag;
    }

}
