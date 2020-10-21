package com.chenkj.myspringboot.controller;

import com.chenkj.myspringboot.service.PromotionService;
import com.chenkj.myspringboot.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author
 * @Description
 * @Date 2020-09-18 14:11
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private PromotionService promotionService;

    @GetMapping("/user/register")
    public Long register(String telephone, String password) {
        long userId = userService.register(telephone, password);
        promotionService.issueNewUserExperienceCash(userId);
        return userId;
    }

}
