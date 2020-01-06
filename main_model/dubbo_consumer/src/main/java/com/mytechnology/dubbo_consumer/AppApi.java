package com.mytechnology.dubbo_consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mytechnology.dubbo_provider.service.ProviderService;

/**
 * @program: main_model
 * @description: 通过Api的方式获取服务
 * @author: ShiYulong
 * @create: 2020-01-06 09:21
 **/
public class AppApi {
    public static void main(String[] args) {
        // 当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer");
        applicationConfig.setOwner("ylstone");

        //连接注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://localhost:2181");
        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        // 引用远程服务
        ReferenceConfig<ProviderService> providerServiceReferenceConfig = new ReferenceConfig<>();
        providerServiceReferenceConfig.setApplication(applicationConfig);
        providerServiceReferenceConfig.setRegistry(registryConfig);
        providerServiceReferenceConfig.setInterface(ProviderService.class);
        // 调用接口
        ProviderService providerService = providerServiceReferenceConfig.get();
        providerService.sayHello("hello dubbo! I am ylstone");
    }
}
