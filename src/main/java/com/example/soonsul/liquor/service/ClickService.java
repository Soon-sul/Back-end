package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.entity.*;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.exception.LocationInfoNotExist;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;
    private final UserUtil userUtil;
    private final LiquorRepository liquorRepository;
    private final LocationRepository locationRepository;
    private final LocationInfoRepository locationInfoRepository;
    private final RegionClickRepository regionClickRepository;
    private final FilteringClickRepository filteringClickRepository;


    @Transactional
    public void postClick(String liquorId){
        final LocalDateTime now= LocalDateTime.now();
        final LocalTime time= LocalTime.of(23,59,59);
        final LocalDateTime nextMonday=
                LocalDateTime.of(now.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY)), time);

        final Click click= Click.builder()
                .liquorId(liquorId)
                .userId(userUtil.getUserByAuthentication().getUserId())
                .ttl(ChronoUnit.SECONDS.between(now, nextMonday))
                .build();
        clickRepository.save(click);
    }


    @Transactional
    public void postRegionClick(String liquorId){
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final List<Location> list= locationRepository.findAllByLiquor(liquor);

        Double latitude= 0.0;
        Double longitude= 0.0;
        if(list.size()>0){
            final LocationInfo info= locationInfoRepository.findById(list.get(0).getLocationInfoId())
                    .orElseThrow(()-> new LocationInfoNotExist("location info not exist",ErrorCode.LOCATION_INFO_NOT_EXIST));
            latitude= info.getLatitude();
            longitude= info.getLongitude();
        }

        final RegionClick regionClick= RegionClick.builder()
                .region(liquor.getRegion())
                .liquorId(liquorId)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        regionClickRepository.save(regionClick);
    }


    @Transactional
    public void postFilteringClick(String liquorId){
        final User user= userUtil.getUserByAuthentication();

        final FilteringClick filteringClick= FilteringClick.builder()
                .age(user.getAge())
                .gender(user.getGender())
                .liquorId(liquorId)
                .build();
        filteringClickRepository.save(filteringClick);
    }


}
