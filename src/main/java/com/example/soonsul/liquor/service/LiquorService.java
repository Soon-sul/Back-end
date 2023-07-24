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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private final LiquorFilteringRepository filteringRepository;
    private final FilteringClickRepository filteringClickRepository;
    private final ScrapRepository scrapRepository;
    private final EvaluationNumberRepository numberRepository;


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

        final List<LiquorFilteringDto> filtering= new ArrayList<>();
        final List<LiquorFiltering> filteringList= filteringRepository.findAllByLiquorId(liquorId);
        for(LiquorFiltering l: filteringList){
            final LiquorFilteringDto dto= LiquorFilteringDto.builder()
                    .age(l.getAge())
                    .gender(l.getGender())
                    .build();
            filtering.add(dto);
        }

        return LiquorInfoDto.builder()
                .name(liquor.getName())
                .ingredient(liquor.getIngredient())
                .averageRating(liquor.getAverageRating())
                .lowestPrice(liquor.getLowestPrice())
                .alcohol(liquor.getAlcohol())
                .capacity(liquor.getCapacity())
                .region(region)
                .imageUrl(liquor.getImageUrl())
                .liquorCategory(liquorCategory)
                .liquorPersonalRating(liquorPersonalRating)
                .ratingNumber(reviewRepository.countByLiquor(liquor))
                .filtering(filtering)
                .flagScrap(scrapRepository.existsByUserAndLiquor(user, liquor))
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


    @Transactional(readOnly = true)
    public List<String> getLiquorListName(){
        return liquorRepository.findAllName();
    }


    @Transactional
    public void updateFiltering(){
        final List<Integer> age= Arrays.asList(20, 30, 40, 50, 60);
        final List<String> gender= Arrays.asList("f", "g");

        filteringRepository.deleteAll();
        for(Integer a: age){
            for(String g: gender){
                final HashMap<String, Integer> map = new HashMap<>();
                final List<FilteringClick> clickList= filteringClickRepository.findAllByAgeAndGender(a,g);

                for(FilteringClick f: clickList){
                    map.put(f.getLiquorId(), (map.get(f.getLiquorId())==null) ? 1: map.get(f.getLiquorId())+1);
                }
                if(map.size()==0) continue;
                List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
                entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                final LiquorFiltering filtering= LiquorFiltering.builder()
                        .age(a)
                        .gender(g)
                        .liquorId(entries.get(0).getKey())
                        .build();
                filteringRepository.save(filtering);
            }
        }
        filteringClickRepository.deleteAll();
    }


    @Transactional
    public void postScrap(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));

        final Scrap scrap= Scrap.builder()
                .scrapDate(LocalDate.now())
                .user(user)
                .liquor(liquor)
                .build();
        scrapRepository.save(scrap);
    }


    @Transactional
    public void deleteScrap(String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));

        scrapRepository.deleteByUserAndLiquor(user, liquor);
    }


    @Transactional(readOnly = true)
    public List<LiquorInfoDto> getScrapList(Pageable pageable, String sorting){
        final User user= userUtil.getUserByAuthentication();
        final List<Liquor> list= liquorRepository.findAll(pageable).toList();

        final List<LiquorInfoDto> result= new ArrayList<>();
        for(Liquor l: list){
            final Optional<Scrap> scrap= scrapRepository.findByUserAndLiquor(user,l);
            if(scrap.isEmpty()) continue;

            final String liquorCategory= codeRepository.findById(l.getLiquorCategory())
                    .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();

            final LiquorInfoDto dto= LiquorInfoDto.builder()
                    .liquorId(l.getLiquorId())
                    .name(l.getName())
                    .averageRating(l.getAverageRating())
                    .lowestPrice(l.getLowestPrice())
                    .imageUrl(l.getImageUrl())
                    .liquorCategory(liquorCategory)
                    .ratingNumber(reviewRepository.countByLiquor(l))
                    .flagScrap(true)
                    .scrapDate(scrap.get().getScrapDate())
                    .build();
            result.add(dto);
        }

        switch (sorting) {
            case "date":
                return byDate(result);
            case "star":
                return byStar(result);
            case "lowest-cost":
                return byLowestCost(result);
            case "highest-cost":
                return byHighestCost(result);
            default:
                return result;
        }
    }


    private List<LiquorInfoDto> byDate(List<LiquorInfoDto> result){
        return result.stream()
                .sorted(Comparator.comparing(LiquorInfoDto::getScrapDate).reversed())
                .collect(Collectors.toList());
    }

    private List<LiquorInfoDto> byStar(List<LiquorInfoDto> result){
        return result.stream()
                .sorted(Comparator.comparing(LiquorInfoDto::getAverageRating).reversed())
                .collect(Collectors.toList());
    }

    private List<LiquorInfoDto> byLowestCost(List<LiquorInfoDto> result){
        return result.stream()
                .sorted(Comparator.comparing(LiquorInfoDto::getLowestPrice))
                .collect(Collectors.toList());
    }

    private List<LiquorInfoDto> byHighestCost(List<LiquorInfoDto> result){
        return result.stream()
                .sorted(Comparator.comparing(LiquorInfoDto::getLowestPrice).reversed())
                .collect(Collectors.toList());
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
