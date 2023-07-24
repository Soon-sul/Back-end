package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.*;
import com.example.soonsul.liquor.response.*;
import com.example.soonsul.liquor.service.*;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags="Liquor")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquors")
public class LiquorController {
    private final LiquorService liquorService;
    private final ClickService clickService;


    @ApiOperation(value = "전통주 상세 정보 조회")
    @GetMapping("/{liquorId}/info")
    public ResponseEntity<LiquorInfoResponse> getLiquorInfo(@PathVariable("liquorId") String liquorId) {
        final LiquorInfoDto data= liquorService.getLiquorInfo(liquorId);
        return ResponseEntity.ok(LiquorInfoResponse.of(ResultCode.GET_LIQUOR_INFO_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 수상내역 조회")
    @GetMapping("/{liquorId}/prizes")
    public ResponseEntity<PrizeListResponse> getLiquorPrize(@PathVariable("liquorId") String liquorId) {
        final List<PrizeListDto> data= liquorService.getLiquorPrize(liquorId);
        return ResponseEntity.ok(PrizeListResponse.of(ResultCode.GET_LIQUOR_PRIZE_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 소재지 조회")
    @GetMapping("/{liquorId}/locations")
    public ResponseEntity<LocationListResponse> getLiquorLocation(@PathVariable("liquorId") String liquorId) {
        final List<LocationListDto> data= liquorService.getLiquorLocation(liquorId);
        return ResponseEntity.ok(LocationListResponse.of(ResultCode.GET_LIQUOR_LOCATION_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 판매처 조회")
    @GetMapping("/{liquorId}/sale-places")
    public ResponseEntity<SalePlaceListResponse> getLiquorSalePlace(@PathVariable("liquorId") String liquorId) {
        final List<SalePlaceListDto> data= liquorService.getLiquorSalePlace(liquorId);
        return ResponseEntity.ok(SalePlaceListResponse.of(ResultCode.GET_LIQUOR_SALE_PLACE_SUCCESS, data));
    }



    @ApiOperation(value = "전통주 조회수 한개 추가")
    @PostMapping("/{liquorId}/click")
    public ResponseEntity<ResultResponse> postClick(@PathVariable("liquorId") String liquorId) {
        clickService.postClick(liquorId);
        clickService.postRegionClick(liquorId);
        clickService.postFilteringClick(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_CLICK_SUCCESS));
    }


    @ApiOperation(value = "모든 전통주 이름 조회")
    @GetMapping("/name")
    public ResponseEntity<ResultResponse> getLiquorListName() {
        final List<String> data= liquorService.getLiquorListName();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_LIQUOR_LIST_NAME_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 필터링 정보 업데이트 - 한달주기")
    @PostMapping("/filtering")
    public ResponseEntity<ResultResponse> updateFiltering() {
        liquorService.updateFiltering();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_LIQUOR_FILTERING_SUCCESS));
    }


    @ApiOperation(value = "스크랩 등록하기")
    @PostMapping("/{liquorId}/scrap")
    public ResponseEntity<ResultResponse> postScrap(@PathVariable("liquorId") String liquorId) {
        liquorService.postScrap(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_SCRAP_SUCCESS));
    }


    @ApiOperation(value = "스크랩 삭제하기")
    @DeleteMapping("/{liquorId}/scrap")
    public ResponseEntity<ResultResponse> deleteScrap(@PathVariable("liquorId") String liquorId) {
        liquorService.deleteScrap(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_SCRAP_SUCCESS));
    }


    @ApiOperation(value = "모든 전통주 평가, 평가수 추가", notes = "전통주 데이터 넣은 후  해당 api 실행하기")
    @PostMapping("/init")
    public ResponseEntity<ResultResponse> postInit() {
        liquorService.postInit();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_LIQUOR_INIT_SUCCESS));
    }
}
