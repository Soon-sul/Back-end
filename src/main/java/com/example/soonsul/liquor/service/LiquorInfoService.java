package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.LiquorInfoDto;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.LiquorInfo;
import com.example.soonsul.liquor.exception.LiquorNoExistException;
import com.example.soonsul.liquor.repository.LiquorInfoRepository;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.response.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiquorInfoService {
    final private LiquorInfoRepository liquorInfoRepository;
    final private LiquorRepository liquorRepository;

    public LiquorInfoDto getLiquorInfo(String liquorId){
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNoExistException("liquor user not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final LiquorInfo liquorInfo= liquorInfoRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNoExistException("liquor user not exist", ErrorCode.LIQUOR_NOT_EXIST));

        return LiquorInfoDto.builder()
                .name(liquor.getName())
                .title(liquorInfo.getTitle())
                .salePlace(liquorInfo.getSalePlace())
                .location(liquorInfo.getLocation())
                .ingredient(liquorInfo.getIngredient())
                .averageRating(liquorInfo.getAverageRating())
                .lowestPrice(liquorInfo.getLowestPrice())
                .alcohol(liquorInfo.getAlcohol())
                .capacity(liquorInfo.getCapacity())
                .viewCount(liquorInfo.getViewCount())
                .latitude(liquorInfo.getLatitude())
                .longitude(liquorInfo.getLongitude())
                .region(liquorInfo.getRegion())
                .imageUrl(liquorInfo.getImageUrl())
                .build();
    }

}
