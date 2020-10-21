package com.chenkj.myspringboot.service;

import org.springframework.stereotype.Service;

/**
 * @Author
 * @Description
 * @Date 2020-09-18 14:17
 */
@Service
public class NotificationService {
    public void sendEmail(Long userId){
        System.out.println("NotificationService-sendEmail");
        System.out.println("dddddd");
    }
}
