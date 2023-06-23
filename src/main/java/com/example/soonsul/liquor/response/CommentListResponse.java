package com.example.soonsul.liquor.response;

import com.example.soonsul.liquor.dto.CommentDto;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "댓글 리스트 응답 모델")
public class CommentListResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<CommentDto> data;


    public CommentListResponse(ResultCode resultCode, List<CommentDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static CommentListResponse of(ResultCode resultCode, List<CommentDto> data) {
        return new CommentListResponse(resultCode, data);
    }
}
