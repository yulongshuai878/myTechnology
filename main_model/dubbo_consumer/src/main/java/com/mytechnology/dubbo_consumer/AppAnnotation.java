package com.mytechnology.dubbo_consumer;

import com.mytechnology.dubbo_consumer.annotation.ConsumerAnnotationService;
import com.mytechnology.dubbo_consumer.configuration.ConsumerConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-06 13:03
 **/
public class AppAnnotation {
    public static void main( String[] args ) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start(); // 启动
        ConsumerAnnotationService consumerAnnotationService = context.getBean(ConsumerAnnotationService.class);
        String hello = consumerAnnotationService.doSayHello("annotation"); // 调用方法
        System.out.println("result: " + hello); // 输出结果

    }
}
