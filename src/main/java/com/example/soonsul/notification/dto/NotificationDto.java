package com.example.soonsul.notification.dto;

import com.example.soonsul.notification.entity.NotificationType;
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

    @ApiModelProperty(value = "알림 대상의 pk(id값)", position = 5)
    private Long objectId;

    @ApiModelProperty(value = "알림 종류 (팔로우, 리뷰 좋아요, 댓글, 대댓글)", position = 6)
    private NotificationType type;
}
