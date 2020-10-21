package com.chenkj.myspringboot.observer;

import com.chenkj.myspringboot.service.PromotionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author
 * @Description
 * @Date 2020-09-18 14:24
 */
@Component
public class RegPromotionObserver implements RegObserver {
    @Resource
    private PromotionService promotionService;

    @Override
    public void handleRegSuccess(long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}
