package com.mytechnology.dubbo_provider.service.annotation;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-06 10:01
 **/
@Service(timeout = 5000)
public class ProviderServiceAnnotationImpl implements ProviderServiceAnnotation{
    @Override
    public String sayHelloAnnotation(String hello) {
        return hello;
    }
}
