package com.example.soonsul.scan;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import com.example.soonsul.scan.dto.ScanDto;
import com.example.soonsul.scan.response.ScanResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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


    @ApiOperation(value = "스캔 완료시, 사진 히스토리에 저장")
    @PostMapping(value = "/liquor/{liquorId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResultResponse> postScan(@PathVariable("liquorId") String liquorId, @RequestPart("image") MultipartFile image) {
        scanService.postScan(liquorId, image);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_SCAN_SUCCESS));
    }


    @ApiOperation(value = "사진히스토리 조회", notes = "10개씩 페이징")
    @GetMapping("/history")
    public ResponseEntity<ScanResponse> getScanList(@PageableDefault(size=10, sort = "scan_id", direction = Sort.Direction.DESC) Pageable pageable) {
        final List<ScanDto> data= scanService.getScanList(pageable);
        return ResponseEntity.ok(ScanResponse.of(ResultCode.GET_SCAN_LIST_SUCCESS, data));
    }


    @ApiOperation(value = "해당 히스토리 삭제")
    @DeleteMapping("/{scanId}")
    public ResponseEntity<ResultResponse> deleteScan(@PathVariable("scanId") Long scanId) {
        scanService.deleteScan(scanId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_SCAN_SUCCESS));
    }
}
