package com.example.soonsul.promotion;

import com.example.soonsul.promotion.dto.PromotionDto;
import com.example.soonsul.promotion.entity.Promotion;
import com.example.soonsul.promotion.exception.PromotionNotExist;
import com.example.soonsul.response.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;


    @Transactional(readOnly = true)
    public List<PromotionDto> getPromotionList(){
        final List<Promotion> list= promotionRepository.findAll();

        final List<PromotionDto> result= new ArrayList<>();
        for(Promotion p: list){
            if(LocalDate.now().isAfter(p.getEndDate())) continue;
            final PromotionDto dto= PromotionDto.builder()
                    .promotionId(p.getPromotionId())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .beginDate(p.getBeginDate())
                    .endDate(p.getEndDate())
                    .location(p.getLocation())
                    .image(p.getImage())
                    .build();
            result.add(dto);
        }

        return result;
    }


    @Transactional(readOnly = true)
    public PromotionDto getPromotion(Long promotionId){
        final Promotion promotion= promotionRepository.findById(promotionId)
                .orElseThrow(()-> new PromotionNotExist("promotion not exist", ErrorCode.PROMOTION_NOT_EXIST));

        return PromotionDto.builder()
                .promotionId(promotion.getPromotionId())
                .title(promotion.getTitle())
                .content(promotion.getContent())
                .beginDate(promotion.getBeginDate())
                .endDate(promotion.getEndDate())
                .location(promotion.getLocation())
                .image(promotion.getImage())
                .build();
    }

}
