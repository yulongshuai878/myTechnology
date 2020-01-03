# springboot整合Redis集群

pom文件增加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-redis</artifactId>
</dependency>

<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
</dependency>
```

配置application.yml或application.properties配置文件

```yml
spring:
  redis:
    cache.clusterNodes: 192.168.32.128:7000,192.168.32.128:7001,192.168.32.128:7002,192.168.32.128:7003,192.168.32.128:7004,192.168.32.128:7005
      cache.commandTimeout: 5000
```

