package com.mytechnology.activemq_provider.controller;

import com.mytechnology.activemq_provider.service.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 09:10
 **/
@RestController
@RequestMapping("/producer")
public class ProviderController {
    @Autowired
    private MqProducer mqProducer;
    @RequestMapping("test1")
    public String test1() {
        mqProducer.sendByStringToQueue("hello queue");
        return "ok";
    }
    @RequestMapping("test2")
    public String test2() {
        mqProducer.sendByStringToTopic("hello topic");
        return "ok";
    }
}
