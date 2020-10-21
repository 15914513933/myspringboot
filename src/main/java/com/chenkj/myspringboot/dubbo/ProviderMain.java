package com.chenkj.myspringboot.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author
 * @Description
 * @Date 2020-09-21 17:33
 */
public class ProviderMain {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-provider.xml"});
        context.start();
        System.out.println("Provider started.");
        System.in.read();
    }
}
