package com.example.soonsul.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "찜 정보")
public class ZzimDto {

    @ApiModelProperty(value = "찜 종류 ex) 메인 배너, 프로모션", position = 1)
    private String category;

    @ApiModelProperty(value = "pk ex) 메인 배너 pk, 프로모션 pk", position = 2)
    private Long objectId;

    @ApiModelProperty(value = "썸네일 url", position = 3)
    private String thumbnail;

    @ApiModelProperty(value = "내용 url", position = 4)
    private String content;

    @ApiModelProperty(value = "제목", position = 5)
    private String title;


    @ApiModelProperty(value = "시작 날짜", position = 6)
    private LocalDate beginDate;

    @ApiModelProperty(value = "종료 날짜", position = 7)
    private LocalDate endDate;

    @ApiModelProperty(value = "위치", position = 8)
    private String location;


    @ApiModelProperty(value = "찜 여부", position = 9)
    private boolean flagZzim;

    @ApiModelProperty(value = "프로모션 관련된 전통주 리스트 (pk)", position = 10)
    private List<String> liquorList;
}
