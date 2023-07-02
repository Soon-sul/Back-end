package com.example.soonsul.promotion;

import com.example.soonsul.promotion.dto.PromotionDto;
import com.example.soonsul.promotion.response.PromotionListResponse;
import com.example.soonsul.promotion.response.PromotionResponse;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="Promotion")
@RestController
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {
    private final PromotionService promotionService;

    @ApiOperation(value = "모든 프로모션 조회")
    @GetMapping()
    public ResponseEntity<PromotionListResponse> getPromotionList() {
        final List<PromotionDto> data= promotionService.getPromotionList();
        return ResponseEntity.ok(PromotionListResponse.of(ResultCode.GET_PROMOTION_LIST_SUCCESS, data));
    }


    @ApiOperation(value = "특정 프로모션 조회")
    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotion(@PathVariable("promotionId") Long promotionId) {
        final PromotionDto data= promotionService.getPromotion(promotionId);
        return ResponseEntity.ok(PromotionResponse.of(ResultCode.GET_PROMOTION_SUCCESS, data));
    }

}
