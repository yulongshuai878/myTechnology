package com.mytechnology.activemq_consumer.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 10:58
 **/
/*
 * 客户控制器
 */
@RestController
public class ConsumerController {
    /*
     * 监听和读取消息
     */
    @JmsListener(destination="ylstone.queue")
    public void readActiveQueue(String message) {
        System.out.println("接受到：" + message);
        //TODO something
    }
}
