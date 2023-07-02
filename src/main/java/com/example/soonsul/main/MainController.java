package com.example.soonsul.main;

import com.example.soonsul.main.dto.WeekLiquorDto;
import com.example.soonsul.main.response.WeekLiquorResponse;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="Main")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;

    @ApiOperation(value = "이번주 가장 사랑받는 전통주")
    @GetMapping("/week-liquor")
    public ResponseEntity<WeekLiquorResponse> getWeekLiquor() {
        final List<WeekLiquorDto> data= mainService.getWeekLiquor();
        return ResponseEntity.ok(WeekLiquorResponse.of(ResultCode.GET_WEEK_LIQUOR_SUCCESS, data));
    }
}
