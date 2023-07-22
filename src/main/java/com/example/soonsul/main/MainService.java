package com.example.soonsul.main;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Location;
import com.example.soonsul.liquor.entity.LocationInfo;
import com.example.soonsul.liquor.entity.RegionClick;
import com.example.soonsul.liquor.exception.CodeNotExist;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.exception.LocationInfoNotExist;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.main.dto.RegionLiquorDto;
import com.example.soonsul.main.dto.WeekLiquorDto;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {
    private final ClickRepository clickRepository;
    private final UserUtil userUtil;
    private final LiquorRepository liquorRepository;
    private final RegionClickRepository regionClickRepository;
    private final ReviewRepository reviewRepository;
    private final LocationRepository locationRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final CodeRepository codeRepository;


    @Transactional(readOnly = true)
    public List<WeekLiquorDto> getWeekLiquor(){
        final PriorityQueue<Pair> pq = new PriorityQueue<>();
        final List<Liquor> liquorList= liquorRepository.findAll();
        for(Liquor l: liquorList){
            pq.add(new Pair(clickRepository.findAllByLiquorId(l.getLiquorId()).size(), l.getLiquorId()));
        }

        final List<String> tenLiquor= new ArrayList<>();
        int ten= 10;
        while(ten>0){
            tenLiquor.add(pq.peek().second);
            pq.remove();
            ten--;
        }

        final List<WeekLiquorDto> result= new ArrayList<>();
        for(String s: tenLiquor){
            final WeekLiquorDto dto= WeekLiquorDto.builder()
                    .liquorId(s)
                    .imageUrl(liquorRepository.findById(s).get().getImageUrl())
                    .build();
            result.add(dto);
        }

        return result;
    }


    @Transactional(readOnly = true)
    public List<RegionLiquorDto> getRegionLiquor(String region, String sorting, Double latitude, Double longitude){
        final List<RegionLiquorDto> result= new ArrayList<>();
        final List<String> codeList= new ArrayList<>();
        switch (region) {
            case "Around-me":
                return aroundMeLiquor(sorting, latitude, longitude);
            case "Metropolitan-area":
                codeList.add("R001");
                codeList.add("R002");
                codeList.add("R003");
                return regionLiquor(sorting, codeList);
            case "Gangwon-do":
                codeList.add("R004");
                return regionLiquor(sorting, codeList);
            case "Chungcheong-do":
                codeList.add("R005");
                codeList.add("R006");
                codeList.add("R012");
                return regionLiquor(sorting, codeList);
            case "Gyeongsang-do":
                codeList.add("R007");
                codeList.add("R008");
                codeList.add("R013");
                codeList.add("R014");
                codeList.add("R015");
                return regionLiquor(sorting, codeList);
            case "Jeolla-do":
                codeList.add("R009");
                codeList.add("R010");
                codeList.add("R016");
                return regionLiquor(sorting, codeList);
            case "Jeju-do":
                codeList.add("R011");
                return regionLiquor(sorting, codeList);
            default:
                return result;
        }
    }


    //내주변
    private List<RegionLiquorDto> aroundMeLiquor(String sorting, Double latitude, Double longitude){
        final HashMap<String, Integer> map = new HashMap<>();   //전통주pk, 클릭수
        final List<RegionClick> clickList= (List<RegionClick>) regionClickRepository.findAll();

        final HashMap<String, Double> disMap = new HashMap<>();    //전통주pk, 거리차
        for(RegionClick r: clickList){
            if(disMap.get(r.getLiquorId())!=null) {    //거리차를 계산한 적 있는 경우
                map.put(r.getLiquorId(), map.get(r.getLiquorId())+1);
                continue;
            }

            final List<Location> locationList= locationRepository.findAllById(r.getLiquorId());
            final LocationInfo info= locationInfoRepository.findById(locationList.get(0).getLocationInfoId())
                    .orElseThrow(() -> new LocationInfoNotExist("location info not exist", ErrorCode.LOCATION_INFO_NOT_EXIST));

            map.put(r.getLiquorId(), (map.get(r.getLiquorId())==null) ? 1: map.get(r.getLiquorId())+1);
            disMap.put(r.getLiquorId(), distanceDifference(latitude, longitude, info.getLatitude(), info.getLongitude()));
        }

        final List<RegionLiquorDto> list= new ArrayList<>();
        for(String liquorId: map.keySet()){
            final Liquor liquor = liquorRepository.findById(liquorId)
                    .orElseThrow(() -> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));

            final List<Location> locations = locationRepository.findAllByLiquor(liquor);
            final List<String> locationList = new ArrayList<>();
            for (Location l : locations) {
                final LocationInfo info = locationInfoRepository.findById(l.getLocationInfoId())
                        .orElseThrow(() -> new LocationInfoNotExist("location info not exist", ErrorCode.LOCATION_INFO_NOT_EXIST));
                locationList.add(info.getBrewery());
            }

            final String liquorCategory= codeRepository.findById(liquor.getLiquorCategory())
                    .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();

            final RegionLiquorDto dto= RegionLiquorDto.builder()
                    .liquorId(liquorId)
                    .name(liquor.getName())
                    .averageRating(liquor.getAverageRating())
                    .alcohol(liquor.getAlcohol())
                    .capacity(liquor.getCapacity())
                    .liquorCategory(liquorCategory)
                    .locationList(locationList)
                    .lowestPrice(liquor.getLowestPrice())
                    .flagZzim(true)     // 추후에 구현 -> 찜기능 추가한 뒤에
                    .ratingNumber(reviewRepository.countByLiquor(liquor))
                    .imageUrl(liquor.getImageUrl())
                    .clickNumber(map.get(liquorId))
                    .distance(disMap.get(liquorId))
                    .build();
            list.add(dto);
        }

        switch (sorting) {
            case "star":
                return byStar(list);
            case "review":
                return byReview(list);
            case "lowest-cost":
                return byLowestCost(list);
            case "highest-cost":
                return byHighestCost(list);
            default:
                return list;
        }
    }

    private Double distanceDifference(Double myLat, Double myLon, Double comLat, Double comLon){
        double R = 6371;
        final Double lat1= Math.toRadians(myLat);
        final Double lon1= Math.toRadians(myLon);
        final Double lat2= Math.toRadians(comLat);
        final Double lon2= Math.toRadians(comLon);

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;

        if(distance<=10000) return distance;
        else return -1.0;
    }


    //지역
    private List<RegionLiquorDto> regionLiquor(String sorting, List<String> codeList){
        final HashMap<String, Integer> map = new HashMap<>();

        for(String regionCode: codeList){
            List<RegionClick> clickList= regionClickRepository.findAllByRegion(regionCode);
            for(RegionClick r: clickList){
                map.put(r.getLiquorId(), (map.get(r.getLiquorId())==null) ? 1: map.get(r.getLiquorId())+1);
            }
        }

        final List<RegionLiquorDto> list= new ArrayList<>();
        for (String liquorId : map.keySet()) {
            final Liquor liquor = liquorRepository.findById(liquorId)
                    .orElseThrow(() -> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));

            final List<Location> locations = locationRepository.findAllByLiquor(liquor);
            final List<String> locationList = new ArrayList<>();
            for (Location l : locations) {
                final LocationInfo info = locationInfoRepository.findById(l.getLocationInfoId())
                        .orElseThrow(() -> new LocationInfoNotExist("location info not exist", ErrorCode.LOCATION_INFO_NOT_EXIST));
                locationList.add(info.getBrewery());
            }

            final String liquorCategory= codeRepository.findById(liquor.getLiquorCategory())
                    .orElseThrow(()->new CodeNotExist("code not exist", ErrorCode.CODE_NOT_EXIST)).getCodeName();

            final RegionLiquorDto dto = RegionLiquorDto.builder()
                    .liquorId(liquorId)
                    .name(liquor.getName())
                    .averageRating(liquor.getAverageRating())
                    .alcohol(liquor.getAlcohol())
                    .capacity(liquor.getCapacity())
                    .liquorCategory(liquorCategory)
                    .locationList(locationList)
                    .lowestPrice(liquor.getLowestPrice())
                    .flagZzim(true)     // 추후에 구현 -> 찜기능 추가한 뒤에
                    .ratingNumber(reviewRepository.countByLiquor(liquor))
                    .imageUrl(liquor.getImageUrl())
                    .clickNumber(map.get(liquorId))
                    .distance(0.0)
                    .build();
            list.add(dto);
        }

        switch (sorting) {
            case "star":
                return byStar(list);
            case "review":
                return byReview(list);
            case "lowest-cost":
                return byLowestCost(list);
            case "highest-cost":
                return byHighestCost(list);
            default:
                return list;
        }
    }


    //필터링
    private List<RegionLiquorDto> byStar(List<RegionLiquorDto> list){
        return list.stream()
                .filter(dto -> dto.getDistance()<=10000)
                .sorted(Comparator.comparing(RegionLiquorDto::getClickNumber).reversed().
                        thenComparing(RegionLiquorDto::getAverageRating).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<RegionLiquorDto> byReview(List<RegionLiquorDto> list){
        return list.stream()
                .filter(dto -> dto.getDistance()<=10000)
                .sorted(Comparator.comparing(RegionLiquorDto::getClickNumber).reversed().
                        thenComparing(RegionLiquorDto::getRatingNumber).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<RegionLiquorDto> byLowestCost(List<RegionLiquorDto> list){
        return list.stream()
                .filter(dto -> dto.getDistance()<=10000)
                .sorted(Comparator.comparing(RegionLiquorDto::getClickNumber).reversed().
                        thenComparing(RegionLiquorDto::getLowestPrice))
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<RegionLiquorDto> byHighestCost(List<RegionLiquorDto> list){
        return list.stream()
                .filter(dto -> dto.getDistance()<=10000)
                .sorted(Comparator.comparing(RegionLiquorDto::getClickNumber).reversed().
                        thenComparing(RegionLiquorDto::getLowestPrice).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

}


class Pair implements Comparable<Pair> {
    double first;
    String second;

    Pair(double f, String s) {
        this.first = f;
        this.second = s;
    }

    public int compareTo(Pair p) {
        if(this.first > p.first) return -1;
        return 1;
    }
}
