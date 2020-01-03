# springboot使用定时任务

### 启动类配置

在启动类中需要加入@EnableScheduling注解，意思是开启定时任务。

```java
@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession
@EnableScheduling
public class MybootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybootApplication.class, args);
    }

}
```

配置定时任务支持多线程

```java
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
    }
}
```

使用定时任务

```java
@Slf4j
@Service
public class TestTask {
    @Scheduled(cron = "*/1 * * * * ?")
    public void execute() {
        log.info("print word.");
        log.info(String.valueOf(System.currentTimeMillis()));
        try {
            Thread.sleep(2000L);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "*/1 * * * * ?")
    public void execute1() {
        log.info(String.valueOf(System.currentTimeMillis()));
        log.info("write message.");
    }
}
```

