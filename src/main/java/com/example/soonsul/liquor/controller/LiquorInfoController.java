package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.LiquorInfoDto;
import com.example.soonsul.liquor.service.LiquorInfoService;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="LiquorInfo")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquor-info")
public class LiquorInfoController {
    private final LiquorInfoService liquorInfoService;

    @ApiOperation(value = "전통주 상세 정보 조회")
    @GetMapping("/{liquorId}")
    public ResponseEntity<ResultResponse> getLiquorInfo(@PathVariable("liquorId") String liquorId) {
        final LiquorInfoDto data= liquorInfoService.getLiquorInfo(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SCANNED_LIQUOR_NAME_SUCCESS, data));
    }

}
