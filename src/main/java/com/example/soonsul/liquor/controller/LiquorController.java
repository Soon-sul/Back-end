package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.LiquorDto;
import com.example.soonsul.liquor.service.LiquorService;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="Liquor")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquor")
public class LiquorController {
    private final LiquorService liquorService;

    @ApiOperation(value = "전통주 상세 정보 조회")
    @GetMapping("/{liquorId}")
    public ResponseEntity<ResultResponse> getLiquor(@PathVariable("liquorId") String liquorId) {
        final LiquorDto data= liquorService.getLiquor(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SCANNED_LIQUOR_NAME_SUCCESS, data));
    }

}
