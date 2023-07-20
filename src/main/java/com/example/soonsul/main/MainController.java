package com.example.soonsul.main;

import com.example.soonsul.main.dto.RegionLiquorDto;
import com.example.soonsul.main.dto.WeekLiquorDto;
import com.example.soonsul.main.response.RegionLiquorResponse;
import com.example.soonsul.main.response.WeekLiquorResponse;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "지금 사랑받는 지역술")
    @GetMapping("/region-liquor")
    public ResponseEntity<RegionLiquorResponse> getRegionLiquor(@RequestParam("region") String region, @RequestParam("sorting") String sorting,
                                                                @RequestParam(value = "latitude", required = false) Double latitude,
                                                                @RequestParam(value = "longitude", required = false) Double longitude) {
        final List<RegionLiquorDto> data= mainService.getRegionLiquor(region, sorting, latitude, longitude);
        return ResponseEntity.ok(RegionLiquorResponse.of(ResultCode.GET_WEEK_LIQUOR_SUCCESS, data));
    }
}
