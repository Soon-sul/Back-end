package com.example.soonsul.scan;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="Scan")
@RestController
@RequiredArgsConstructor
@RequestMapping("/scan")
public class ScanController {
    private final ScanService scanService;

    @ApiOperation(value = "스캔한 제품의 주류명 조회")
    @GetMapping("/liquor")
    public ResponseEntity<ResultResponse> getLiquor(@RequestParam String name) {
        final String data= scanService.getLiquor(name);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SCANNED_LIQUOR_NAME_SUCCESS, data));
    }
}
