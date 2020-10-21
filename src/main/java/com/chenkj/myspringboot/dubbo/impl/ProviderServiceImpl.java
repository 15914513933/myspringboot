package com.chenkj.myspringboot.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chenkj.myspringboot.dubbo.ProviderService;
import org.springframework.stereotype.Component;

/**
 * @Author
 * @Description
 * @Date 2020-09-21 17:21
 */
@Component
@Service
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String helloHi(String name) {
        return "Hi " + name;
    }
}
