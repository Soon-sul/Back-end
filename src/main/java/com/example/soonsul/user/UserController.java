package com.example.soonsul.user;

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

import java.util.List;

@Api(tags="User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


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


}
