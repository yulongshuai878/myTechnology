package com.mytechnology.dubbo_consumer.annotation;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mytechnology.dubbo_provider.service.annotation.ProviderServiceAnnotation;
import org.springframework.stereotype.Component;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-06 10:10
 **/
@Component("annotatedConsumer")
public class ConsumerAnnotationService {
    @Reference
    private ProviderServiceAnnotation providerServiceAnnotation;

    public String doSayHello(String name) {
        return providerServiceAnnotation.sayHelloAnnotation(name);
    }
}
