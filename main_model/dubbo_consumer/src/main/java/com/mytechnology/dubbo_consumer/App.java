package com.mytechnology.dubbo_consumer;

import com.mytechnology.dubbo_provider.service.ProviderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-06 08:50
 **/
public class App {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        ProviderService providerService = (ProviderService) context.getBean("providerService");
        String str = providerService.sayHello("hello");
        System.out.println(str);
        System.in.read();
    }
}
