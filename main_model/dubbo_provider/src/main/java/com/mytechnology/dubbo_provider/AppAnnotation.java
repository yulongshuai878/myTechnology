package com.mytechnology.dubbo_provider;

import com.mytechnology.dubbo_provider.configuration.DubboConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-06 10:04
 **/
public class AppAnnotation {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboConfiguration.class);
        context.start();
        System.in.read();
    }
}
