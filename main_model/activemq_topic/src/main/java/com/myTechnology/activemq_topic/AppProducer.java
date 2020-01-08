package com.myTechnology.activemq_topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-08 11:08
 **/
public class AppProducer {
    private static final String url = "tcp://192.168.32.131:61616";
    private static final String topicName = "topic-test";

    public static void main(String[] args) throws JMSException {
        //1.创建ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标
        Destination destination = session.createTopic(topicName);
        //6.创建一个生产者
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 10; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            //8.发布消息
            producer.send(textMessage);

            System.out.println("发送消息"+textMessage.getText());
        }

        //9.关闭连接
        connection.close();

    }
}
