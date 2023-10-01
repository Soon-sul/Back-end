package com.example.soonsul.notification;

import com.example.soonsul.notification.dto.NotificationDto;
import com.example.soonsul.notification.dto.PushNotification;
import com.example.soonsul.notification.entity.NotificationType;
import com.example.soonsul.notification.entity.Notifications;
import com.example.soonsul.notification.exception.NotificationNotExist;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.util.UserUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserUtil userUtil;
    private final NotificationRepository notificationRepository;


    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationList(Pageable pageable){
        final User user= userUtil.getUserByAuthentication();
        final List<Notifications> list= notificationRepository.findAllByUser(pageable, user.getUserId()).toList();

        final List<NotificationDto> result= new ArrayList<>();
        for(Notifications notification: list){
            final NotificationDto dto= NotificationDto.builder()
                    .notificationId(notification.getNotificationId())
                    .content(notification.getContent())
                    .date(dateConversion(notification.getDate()))
                    .flagRead(notification.isFlagRead())
                    .build();
            result.add(dto);
        }
        return result.stream().filter(dto-> dto.getDate()!=null).collect(Collectors.toList());
    }

    private String dateConversion(LocalDateTime request){
        final Date notificationDate= Date.from(request.atZone(ZoneId.systemDefault()).toInstant());
        final Date now= Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = calendar.getTime();

        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startOfWeek = calendar.getTime();

        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startOfMonth = calendar.getTime();

        if (notificationDate.equals(today)) return "오늘";
        else if (notificationDate.equals(yesterday)) return "어제";
        else if (notificationDate.after(startOfWeek)) return "이번주";
        else if (notificationDate.after(startOfMonth)) return "이번달";
        else return null;
    }


    @Transactional(readOnly = true)
    public boolean getNewNotification(){
        final User user= userUtil.getUserByAuthentication();
        return !notificationRepository.findNewNotification(user.getUserId()).isEmpty();
    }


    @Transactional
    public void putNewNotification(List<Long> idList){
        for(Long id: idList){
            final Notifications notification= notificationRepository.findById(id)
                    .orElseThrow(()-> new NotificationNotExist("notification not exist", ErrorCode.NOTIFICATION_NOT_EXIST));
            notification.updateFlagRead(true);
        }
    }


    @Transactional
    public void sendNotification(NotificationType type, PushNotification pushNotification) throws FirebaseMessagingException {
        final String token = pushNotification.getReceiveUser().getDeviceToken();
        final User user= userUtil.getUserByAuthentication();
        final String content = getContent(type, user.getNickname());

        saveNotifications(content, type, pushNotification.getObjectId(), user.getUserId(), pushNotification.getReceiveUser());
        sendPersonalAlarm(content, token);
    }

    private String getContent(NotificationType type, String nickName){
        switch (type){
            case FOLLOW:
                return nickName+ "님이 팔로우했습니다.";
            case REVIEW_GOOD:
                return nickName+ "님이 회원님의 리뷰를 좋아합니다.";
            case COMMENT:
                return nickName+ "님이 회원님의 리뷰에 댓글을 남겼습니다.";
            case RECOMMENT:
                return nickName+ "님이 회원님의 댓글에 새로운 댓글을 남겼습니다.";
            default:
                return null;
        }
    }

    private void saveNotifications(String content, NotificationType type, Long objectId, String sendUserId, User receiveUser){
        Notifications notification = Notifications.builder()
                .content(content)
                .date(LocalDateTime.now())
                .flagRead(false)
                .type(type)
                .objectId(objectId)
                .sendUserId(sendUserId)
                .receiveUser(receiveUser)
                .build();
        notificationRepository.save(notification);
    }

    private void sendPersonalAlarm(String content, String token) throws FirebaseMessagingException {
        Message message = Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle("순술").setBody(content).build())
                .setToken(token)
                .build();
        String response = FirebaseMessaging.getInstance().send(message);
        log.info("메시지 전송 알림 완료 : " + response);
    }

}
