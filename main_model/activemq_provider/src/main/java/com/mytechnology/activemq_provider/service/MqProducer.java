package com.mytechnology.activemq_provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 09:07
 **/
@Service
public class MqProducer {
    @Autowired
    private Destination queue;
    @Autowired
    private Destination topic;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendByStringToQueue(String msg) {
        jmsMessagingTemplate.convertAndSend(queue,msg);
    }
    public void sendByStringToTopic(String msg) {
        jmsMessagingTemplate.convertAndSend(topic,msg);
    }
}
