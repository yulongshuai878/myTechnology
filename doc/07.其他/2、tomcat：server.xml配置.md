# 2、tomcat：server.xml配置

## 一、server.xml详解

###  **1、Server**

​    server.xml的最外层元素。

**常用属性：**

​    port：Tomcat监听shutdown命令的端口。

​    shutdown：通过指定的端口（port）关闭Tomcat所需的字符串。修改shutdown的值，对shutdown.bat无影响

###  **2. Listener**

​    Listener即监听器，负责监听特定的事件，当特定事件触发时，Listener会捕捉到该事件，并做出相应处理。Listener通常用在Tomcat的启动和关闭过程。Listener可嵌在Server、Engine、Host、Context内

**常用属性：**

​    className：指定实现org.apache.catalina.LifecycleListener接口的类

### **3. GlobalNamingResources**

​    GlobalNamingResources用于配置JNDI

### **4. Service**

​    Service包装Executor、Connector、Engine，以组成一个完整的服务

**常用属性：**

​    className：指定实现org.apache.catalina. Service接口的类，默认值为org.apache.catalina.core.StandardService

​    name：Service的名字

​    Server可以包含多个Service组件