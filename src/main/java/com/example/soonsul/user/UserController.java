package com.example.soonsul.user;

import com.example.soonsul.liquor.dto.LiquorInfoDto;
import com.example.soonsul.liquor.response.LiquorInfoListResponse;
import com.example.soonsul.liquor.service.LiquorService;
import com.example.soonsul.main.dto.RegionLiquorDto;
import com.example.soonsul.promotion.PromotionService;
import com.example.soonsul.promotion.dto.PromotionDto;
import com.example.soonsul.promotion.response.PromotionListResponse;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import com.example.soonsul.scan.ScanService;
import com.example.soonsul.scan.dto.ScanDto;
import com.example.soonsul.scan.response.ScanResponse;
import com.example.soonsul.search.dto.SearchDto;
import com.example.soonsul.search.response.SearchResponse;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags="User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PromotionService promotionService;
    private final ScanService scanService;
    private final LiquorService liquorService;


    @ApiOperation(value = "유저 프로필 변경")
    @PutMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResultResponse> putProfile(@RequestPart("nickname") String nickname, @RequestPart("image") MultipartFile image) {
        userService.putProfile(nickname, image);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PUT_PROFILE_SUCCESS));
    }


    @ApiOperation(value = "유저 닉네임 사용여부(중복확인)", notes = "true: 닉네임 사용할 수 있음, false: 사용할 수 없음")
    @GetMapping("/nickname/check")
    public ResponseEntity<ResultResponse> getNicknameCheck(@RequestParam("nickname") String nickname) {
        final boolean data= userService.getNicknameCheck(nickname);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_NICKNAME_CHECK_SUCCESS, data));
    }


    @ApiOperation(value = "유저 찜 리스트 조회")
    @GetMapping("/zzims")
    public ResponseEntity<PromotionListResponse> getUserZzim() {
        final List<PromotionDto> result= promotionService.getPromotionList();
        final List<PromotionDto> data= result.stream().filter(PromotionDto::isFlagZzim).collect(Collectors.toList());
        return ResponseEntity.ok(PromotionListResponse.of(ResultCode.GET_USER_ZZIM_SUCCESS, data));
    }


    @ApiOperation(value = "사진 히스토리 조회")
    @GetMapping("/histories")
    public ResponseEntity<ScanResponse> getUserHistory(@PageableDefault(size=10, sort = "scan_id", direction = Sort.Direction.DESC) Pageable pageable) {
        final List<ScanDto> data= scanService.getScanList(pageable);
        return ResponseEntity.ok(ScanResponse.of(ResultCode.GET_USER_HISTORY_SUCCESS, data));
    }


    @ApiOperation(value = "스크랩한 전통주 조회", notes = "sorting: date, star, lowest-cost, highest-cost")
    @GetMapping("/scraps")
    public ResponseEntity<LiquorInfoListResponse> getUserScrap(@PageableDefault(size=10) Pageable pageable, String sorting) {
        final List<LiquorInfoDto> data= liquorService.getScrapList(pageable, sorting);
        return ResponseEntity.ok(LiquorInfoListResponse.of(ResultCode.GET_USER_SCRAP_SUCCESS, data));
    }
}
