package com.example.soonsul.notification.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "알림 정보")
public class NotificationDto {

    @ApiModelProperty(value = "알림 pk", position = 1)
    private Long notificationId;

    @ApiModelProperty(value = "알림 내용", position = 2)
    private String content;

    @ApiModelProperty(value = "알림 날짜", position = 3)
    private String date;

    @ApiModelProperty(value = "알림 확인 유무 (새로운 알림:false, 이전 알림:true)", position = 4)
    private boolean flagRead;
}
