package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.LiquorDto;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.exception.LiquorNoExistException;
import com.example.soonsul.liquor.repository.LiquorRepository;
import com.example.soonsul.response.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiquorService {
    final private LiquorRepository liquorRepository;

    @Transactional(readOnly = true)
    public LiquorDto getLiquor(String liquorId){
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNoExistException("liquor user not exist", ErrorCode.LIQUOR_NOT_EXIST));

        return LiquorDto.builder()
                .name(liquor.getName())
                .salePlace(liquor.getSalePlace())
                .location(liquor.getLocation())
                .ingredient(liquor.getIngredient())
                .averageRating(liquor.getAverageRating())
                .lowestPrice(liquor.getLowestPrice())
                .alcohol(liquor.getAlcohol())
                .capacity(liquor.getCapacity())
                .viewCount(liquor.getViewCount())
                .latitude(liquor.getLatitude())
                .longitude(liquor.getLongitude())
                .region(liquor.getRegion())
                .imageUrl(liquor.getImageUrl())
                .liquorCatory(liquor.getLiquorCatory())
                .brewery(liquor.getBrewery())
                .build();
    }
}
