package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.entity.Click;
import com.example.soonsul.liquor.repository.ClickRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;
    private final UserUtil userUtil;


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

}
