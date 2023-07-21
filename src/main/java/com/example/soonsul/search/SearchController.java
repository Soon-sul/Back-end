package com.example.soonsul.search;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import com.example.soonsul.search.dto.SearchDto;
import com.example.soonsul.search.response.SearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="Search")
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;


    @ApiOperation(value = "스캔한 제품의 주류명 조회")
    @GetMapping()
    public ResponseEntity<SearchResponse> getSearch(@RequestParam("name") String name) {
        final List<SearchDto> data= searchService.getSearch(name);
        return ResponseEntity.ok(SearchResponse.of(ResultCode.GET_SEARCH_SUCCESS, data));
    }
}
