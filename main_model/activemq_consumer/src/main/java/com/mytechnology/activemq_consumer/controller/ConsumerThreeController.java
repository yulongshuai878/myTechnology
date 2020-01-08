package com.mytechnology.activemq_consumer.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 11:01
 **/
@RestController
public class ConsumerThreeController
{
    @JmsListener(destination="ylstone.topic")
    public void readActiveTopic(String message) {
        System.out.println("接受到3：" + message);
        //TODO something
    }
}
