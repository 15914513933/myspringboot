package com.chenkj.myspringboot.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author
 * @Description
 * @Date 2020-09-27 15:43
 */
public class ConsumerMain {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-consumer.xml"});
        context.start();
        // Obtaining a remote service proxy
        ProviderService providerService = (ProviderService) context.getBean("providerService");
        // Executing remote methods
        String msg = providerService.helloHi("chenkj");
        // Display the call result
        System.out.println(msg);
    }
}
