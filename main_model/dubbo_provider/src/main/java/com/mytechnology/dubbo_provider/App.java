package com.mytechnology.dubbo_provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-03 16:01
 **/
public class App {
    public static void main( String[] args ) throws IOException {
        //加载xml配置文件启动
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/provider.xml");
        context.start();
        System.in.read(); // 按任意键退出
    }
}
