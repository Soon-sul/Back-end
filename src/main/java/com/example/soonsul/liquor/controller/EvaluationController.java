package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.EvaluationDto;
import com.example.soonsul.liquor.dto.EvaluationRequest;
import com.example.soonsul.liquor.dto.PersonEvaluationDto;
import com.example.soonsul.liquor.response.EvaluationResponse;
import com.example.soonsul.liquor.response.PersonEvaluationResponse;
import com.example.soonsul.liquor.service.EvaluationService;
import com.example.soonsul.liquor.service.LiquorService;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="Evaluation")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquors")
public class EvaluationController {
    private final LiquorService liquorService;
    private final EvaluationService evaluationService;


    @ApiOperation(value = "전통주 평가 여부 - 평점에 대한 평가 여부도 됨")
    @GetMapping("/{liquorId}/evaluation/check")
    public ResponseEntity<ResultResponse> getEvaluationCheck(@PathVariable("liquorId") String liquorId) {
        final boolean data= liquorService.getEvaluationCheck(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_EVALUATION_CHECK_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 전체 맛평가 조회")
    @GetMapping("/{liquorId}/flavor-evaluation/average")
    public ResponseEntity<EvaluationResponse> getFlavorAverage(@PathVariable("liquorId") String liquorId) {
        final EvaluationDto data= liquorService.getFlavorAverage(liquorId);
        return ResponseEntity.ok(EvaluationResponse.of(ResultCode.GET_FLAVOR_AVERAGE_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 개인 맛평가 조회")
    @GetMapping("/{liquorId}/flavor-evaluation/person")
    public ResponseEntity<PersonEvaluationResponse> getFlavorPerson(@PathVariable("liquorId") String liquorId) {
        final PersonEvaluationDto data= liquorService.getFlavorPerson(liquorId);
        return ResponseEntity.ok(PersonEvaluationResponse.of(ResultCode.GET_FLAVOR_PERSON_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 맛평가 여부")
    @GetMapping("/{liquorId}/flavor-evaluation/check")
    public ResponseEntity<ResultResponse> getFlavorCheck(@PathVariable("liquorId") String liquorId) {
        final boolean data= liquorService.getFlavorCheck(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_FLAVOR_CHECK_SUCCESS, data));
    }


    @ApiOperation(value = "전통주에 대한 평가 등록")
    @PostMapping("/{liquorId}/evaluation")
    public ResponseEntity<ResultResponse> postEvaluation(@PathVariable("liquorId") String liquorId, @RequestBody EvaluationRequest request) {
        evaluationService.postEvaluation(liquorId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_EVALUATION_SUCCESS));
    }


    @ApiOperation(value = "전통주에 대한 평가 수정")
    @PutMapping("/{liquorId}/evaluation")
    public ResponseEntity<ResultResponse> putEvaluation(@PathVariable("liquorId") String liquorId, @RequestBody EvaluationRequest request) {
        evaluationService.putEvaluation(liquorId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PUT_EVALUATION_SUCCESS));
    }

}