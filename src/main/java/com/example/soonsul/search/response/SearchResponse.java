package com.example.soonsul.search.response;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.search.dto.SearchDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "검색한 전통주 리스트 응답 모델")
public class SearchResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<SearchDto> data;


    public SearchResponse(ResultCode resultCode, List<SearchDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static SearchResponse of(ResultCode resultCode, List<SearchDto> data) {
        return new SearchResponse(resultCode, data);
    }
}
