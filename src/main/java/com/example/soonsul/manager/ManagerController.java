package com.example.soonsul.manager;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags="관리자")
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;


    @ApiOperation(value = "모든 전통주 메인사진 s3에 등록")
    @PostMapping(value = "/liquor/main-photo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResultResponse> postMainPhoto(@RequestPart("images") List<MultipartFile> images) {
        managerService.postMainPhoto(images);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_MAIN_PHOTO));
    }


    @ApiOperation(value = "모든 전통주 평가, 평가수 추가", notes = "전통주 데이터 넣은 후  해당 api 실행하기")
    @PostMapping("/liquor/init")
    public ResponseEntity<ResultResponse> postLiquorInit() {
        managerService.postLiquorInit();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_LIQUOR_INIT_SUCCESS));
    }


    @ApiOperation(value = "모든 소재지에 위도,경도값 추가", notes = "소재지 데이터 넣은 후  해당 api 실행하기")
    @PostMapping("/location/init")
    public ResponseEntity<ResultResponse> postLocationInit() {
        managerService.postLocationInit();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_LOCATION_INIT_SUCCESS));
    }

}
