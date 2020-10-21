package com.chenkj.myspringboot.observer;

import com.chenkj.myspringboot.service.NotificationService;
import com.chenkj.myspringboot.service.PromotionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author
 * @Description
 * @Date 2020-09-18 14:24
 */
@Component
public class RegNotificationObserver implements RegObserver {
    @Resource
    private NotificationService notificationService;

    @Override
    public void handleRegSuccess(long userId) {
        notificationService.sendEmail(userId);
    }
}
