# springboot整合Jersey

## 1.什么是jersey

阅读官方文档请点击:[jsersey](https://jersey.java.net/)。RESTful Web Services in Java即java中的一种restful框架。jersey使用了JAX-RS规范来约束API的开发。既然jersey是基于restful风格的框架,那么什么是restful呢,主要有以下几点:

- 在rest认为，一切都可以被称为资源。
- 每个资源都由uri标识。要访问这个资源，必须通过对应的uri去访问。
- 访问资源使用POST，GET，PUT，DELETE。POST为新增接口,GET为获取接口,PUT为修改接口,DELETE为删除接口。
- 通过XML/JSON去通信
- 每次请求都是独立的。

## 2.为什么要使用springboot+jersey

如果要实现rest，jersey是一个很不错的选择。springboot是java中一个轻量级的框架，能简化配置，不复杂且功能齐全，因此结合起来使用，也是一个不错的选择。

## 3.如何整合springboot与jersey

添加jersey依赖，在pom.xml中添加依赖,在控制台执行命令mvn install

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-jersey</artifactId>
</dependency>
```

创建jersey配置文件

```java
@Component
public class JerseyConfig extends ResourceConfig {
 
    public JerseyConfig() {
        //构造函数，在这里注册需要使用的内容，（过滤器，拦截器，API等）
    }
```

资源，即API，这里以get方法为例:

```java
@Component
@Path("/jersey")
public class JerseyController {
    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserBean get(){
        UserBean userBean = new UserBean();
        userBean.setName("ylstone");
        userBean.setAge(30);
        return userBean;
    }
}
```

jersey配置(有两种注册方式，注册类，注册包):

```java
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        //构造函数，在这里注册需要使用的内容，（过滤器，拦截器，API等）
        packages("com.study.myboot.controller");
    }
}
```

