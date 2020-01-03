package com.mytechnology.dubbo_provider.service;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-03 15:52
 **/
public class ProviderServiceImpl implements ProviderService{
    @Override
    public String sayHello(String word) {
        return "hello " + word;
    }
}
