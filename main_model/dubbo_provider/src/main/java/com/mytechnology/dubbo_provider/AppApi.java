package com.mytechnology.dubbo_provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.mytechnology.dubbo_provider.service.ProviderService;
import com.mytechnology.dubbo_provider.service.ProviderServiceImpl;

/**
 * @program: main_model
 * @description: 通过Api的方式暴露接口
 * @author: ShiYulong
 * @create: 2020-01-06 09:10
 **/
public class AppApi {
    public static void main(String[] args) throws Exception {
        // 服务实现
        ProviderService providerService = new ProviderServiceImpl();
        // 当前配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("provider");
        applicationConfig.setOwner("ylstone");

        // 连接注册中心配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://locakhost:2181");

        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);

        // 服务提供者暴露服务配置
        ServiceConfig<ProviderService> serviceServiceConfig = new ServiceConfig<>();
        serviceServiceConfig.setApplication(applicationConfig);
        serviceServiceConfig.setRegistry(registryConfig);
        serviceServiceConfig.setProtocol(protocolConfig);
        serviceServiceConfig.setInterface(ProviderService.class);
        serviceServiceConfig.setRef(providerService);
        serviceServiceConfig.setVersion("1.0.0");

        // 暴露及注册服务
        serviceServiceConfig.export();
    }
}
