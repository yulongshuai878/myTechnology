package com.myTechnology.activemq_topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 11:09
 **/
public class AppConsumer {
    private static final String url = "tcp://192.168.32.131:61616";
    private static final String topicName = "ylstone.topic";

    public static void main(String[] args) throws JMSException {
        //1. 创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2. 创建Connection
        Connection connection = connectionFactory.createConnection();
        //3. 启动连接
        connection.start();
        //4. 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. 创建一个目标
        Destination destination = session.createTopic(topicName);
        //6. 创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //7. 创建一个监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    System.out.println("接收消息  = [" + ((TextMessage) message).getText() + "]");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //8.关闭连接
        //connection.close();
    }
}