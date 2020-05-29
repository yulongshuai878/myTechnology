package com.mytechnology.activemq_consumer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 09:52
 **/
@Component
public class MqConsumer {
    // 使用JmsListener配置消费者监听的队列，其中name是接收到的消息
    @JmsListener(destination = "springboot.queue2",containerFactory = "jmsListenerContainerQueue")
    // SendTo 会将此方法返回的数据, 写入到 OutQueue 中去.
    @SendTo("SQueue")
    public String handleMessage(String name) {
        System.out.println("成功接受queue" + name);
        return "成功接受queue:" + name;
    }
    @JmsListener(destination = "ylstone.topic")
    public String topicMessage(String msg) {
        System.out.println("成功接受topic:" + msg);
        return "成功接受topic" + msg;
    }

    //接收topic消息
    @JmsListener(destination = "ylstone.topic")
    public void handlerTopic(String msessage){
        System.out.println(msessage);
    }
}
