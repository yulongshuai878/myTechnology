package com.mytechnology.activemq_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class ActivemqProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqProviderApplication.class, args);
    }

}
