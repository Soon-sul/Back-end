package com.example.soonsul.user;

import com.example.soonsul.main.dto.RegionLiquorDto;
import com.example.soonsul.promotion.PromotionService;
import com.example.soonsul.promotion.dto.PromotionDto;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import com.example.soonsul.search.dto.SearchDto;
import com.example.soonsul.search.response.SearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ResultResponse> getUserZzim() {
        final List<PromotionDto> result= promotionService.getPromotionList();
        final List<PromotionDto> data= result.stream().filter(PromotionDto::isFlagZzim).collect(Collectors.toList());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_ZZIM_SUCCESS, data));
    }
}
