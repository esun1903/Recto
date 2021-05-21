package com.ssafy.recto.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private Logger log = LoggerFactory.getLogger(NotificationManager.class);

    @Autowired
    @Lazy // 순환 관계 임시 방편
    private MattermostSender mmSender;

    public void sendNotification(Exception e, String uri, String params) {
        log.info("#### SEND Notification");
        mmSender.sendMessage(e, uri, params);
    }

}