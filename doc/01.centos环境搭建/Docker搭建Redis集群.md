# Docker搭建Redis集群

本教程是基于CentOS 7，Redis 4.0.1，Docker 19.03.5，Docker镜像使用的阿里云。

## 1. 安装Docker

自行安装配置镜像

## 2. 宿主机安装Redis

创建目录并下载redis安装包

```shell
mkdir /data
cd /data
wget http://download.redis.io/releases/redis-4.0.1.tar.gz
```

> /data为自定义目录，可修改为自己需要安装的目录
>
> Redis其他版本可在[redis下载](http://download.redis.io/releases/)自行获取

解压安装包

```shell
tar -zxvf redis-4.0.1.tar.gz
```

安装gcc，用于编译

```shell
yum install -y gcc-c++
```

编译

```shell
make
```

配置redis.conf，源文件内容很多，只需要修改一下几个配置

- ip绑定

```shell
bind 0.0.0.0
```

> 0.0.0.0为不绑定ip

- 设置密码

```shell
requirepass 123456789
```

> 密码自行配置

- 主从复制验证密码

```shell
masterauth 123456789
```

> 密码自行配置，建议与上面的密码一致，便于记忆

- 日志文件

```shell
logfile "/data/logs/redis-server.log"
```

> 日志路径可自行修改为需要存放日志的位置

- 开启集群

```shell
cluster-enabled yes
```

- 集群配置文件

```shell
cluster-config-file nodes-6379.conf
```

- 集群超时时间

```shell
cluster-node-timeout 15000
```

## 3. 制作Redis基础镜像

编写生成基础镜像的Dockerfile

```shell
mkdir redis-default
cd redis-default
touch Dockerfile
vim Dockerfile
```

复制以下内容

```dockerfile
#基础镜像
FROM centos:7
#维护人
MAINTAINER  、T "93846764@qq.com"
#定义redis目录变量
ENV REDIS_HOME /usr/local
#将redis安装包添加到镜像根目录
ADD redis-4.0.1.tar.gz /
#更新yum资源  #安装编译环境  #创建redis目录
RUN yum -y update && \
    yum install -y gcc make && \
    mkdir -p $REDIS_HOME/redis
#将redis.conf添加到redis目录中
ADD redis.conf $REDIS_HOME/redis/
#变更工作空间
WORKDIR /redis-4.0.1
#编译  #将redis-server移动到redis目录中
RUN make && \
    mv /redis-4.0.1/src/redis-server $REDIS_HOME/redis/
#变更工作空间
WORKDIR /
#创建日志目录，注意与redis.conf中相同  #赋予权限  #删除redis目录  #卸载编译环境
RUN mkdir -p /data/logs && \
    chmod -R 777 /data/logs && \
    rm -rf /redis-4.0.1 && \
    yum remove -y gcc make
#挂载日志目录
VOLUME [/data/logs]
#开放端口
EXPOSE 6379
```

将redis安装包与redis.conf复制到/data/redis-default目录下

```shell
cp /data/redis-4.0.1.tar.gz /data/redis-default/
cp /data/redis-4.0.1/redis.conf /data/redis-default/
```

生成基础镜像

```shell
docker build -t redis-default .
```

> 要注意，在最后有一个   `.`         这个点代表的是当前目录下的Dockerfile

```shell
Step 18/18 : EXPOSE 6379
 ---> Using cache
 ---> 3f94cfdcc5d1
Successfully built 3f94cfdcc5d1
Successfully tagged redis-default:latest
```

出现此日志表示镜像构建成功。如出现错误日志，请根据具体日志信息修改即可

使用`docker images`查看本地镜像

```shell
[root@localhost redis-docker-file]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
redis-default       latest              3f94cfdcc5d1        3 hours ago         611MB
centos              7                   5e35e350aded        5 weeks ago         203MB
hello-world         latest              fce289e99eb9        11 months ago       1.84kB
```

## 4. 制作Redis节点镜像

创建用于构建redis节点镜像的Dockerfile

```shell
mkdir redis-node
cd redis-node
touch Dockerfile
vim Dockerfile
```

粘贴一下内容

```dockerfile
#基于上面构建的基础镜像
FROM redis-default:latest
#镜像启动后执行的命令
ENTRYPOINT ["/usr/local/redis/redis-server","/usr/local/redis/redis.conf"]
```

构建redis节点镜像

```shell
docker build -t redis-node .
```

```shell
Successfully tagged redis-node:latest
```

以上日志表示构建成功。

`docker images`查看镜像

```shell
[root@localhost redis-docker-file]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
redis-node          latest              f7c3b6a24247        3 hours ago         611MB
redis-default       latest              3f94cfdcc5d1        3 hours ago         611MB
centos              7                   5e35e350aded        5 weeks ago         203MB
hello-world         latest              fce289e99eb9        11 months ago       1.84kB
```

## 5. 配置集群

启动redis节点

```shell
docker run -d --name redis-node-7000 -p 7000:6379 redis-node
docker run -d --name redis-node-7001 -p 7001:6379 redis-node
docker run -d --name redis-node-7002 -p 7002:6379 redis-node
docker run -d --name redis-node-7003 -p 7003:6379 redis-node
docker run -d --name redis-node-7004 -p 7004:6379 redis-node
docker run -d --name redis-node-7005 -p 7005:6379 redis-node
```

> 宿主机的7000-7005端口分别映射到6个容器的6379端口

`docker ps`查看运行容器

```shell
[root@localhost redis-node-file]# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
e0dc8aca6675        redis-node          "/usr/local/redis/re…"   3 hours ago         Up 3 hours          0.0.0.0:7005->6379/tcp   redis-node-7005
593ca386ab02        redis-node          "/usr/local/redis/re…"   3 hours ago         Up 3 hours          0.0.0.0:7004->6379/tcp   redis-node-7004
922da6c61c4c        redis-node          "/usr/local/redis/re…"   3 hours ago         Up 3 hours          0.0.0.0:7003->6379/tcp   redis-node-7003
4537956e67b9        redis-node          "/usr/local/redis/re…"   3 hours ago         Up 3 hours          0.0.0.0:7002->6379/tcp   redis-node-7002
6295eac17687        redis-node          "/usr/local/redis/re…"   3 hours ago         Up 3 hours          0.0.0.0:7001->6379/tcp   redis-node-7001
f693b6a67aa5        redis-node          "/usr/local/redis/re…"   3 hours ago         Up 3 hours          0.0.0.0:7000->6379/tcp   redis-node-7000
```

查看容器内网下关联的ip

```shell
[root@localhost redis-node-file]# docker inspect e0dc8aca6675 593ca386ab02 922da6c61c4c 4537956e67b9 6295eac17687 f693b6a67aa5 | grep IPAddress
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.7",
                    "IPAddress": "172.17.0.7",
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.6",
                    "IPAddress": "172.17.0.6",
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.5",
                    "IPAddress": "172.17.0.5",
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.4",
                    "IPAddress": "172.17.0.4",
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.3",
                    "IPAddress": "172.17.0.3",
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.2",
                    "IPAddress": "172.17.0.2",
```

宿主机与容器ip的映射关系如下

|    宿主机ip端口    |   容器ip端口    |
| :----------------: | :-------------: |
| 192.168.9.101:7000 | 172.17.0.2:6379 |
| 192.168.9.101:7001 | 172.17.0.3:6379 |
| 192.168.9.101:7002 | 172.17.0.4:6379 |
| 192.168.9.101:7003 | 172.17.0.5:6379 |
| 192.168.9.101:7004 | 172.17.0.6:6379 |
| 192.168.9.101:7005 | 172.17.0.7:6379 |

节点配置

- 先进入任意一个节点

```shell
/data/redis-4.0.1/src/redis-cli -p 7000
auth 123456789
```

- 添加节点

将所有的redis节点添加入集群

```shell
CLUSTER MEET 172.17.0.2 6379
CLUSTER MEET 172.17.0.3 6379
CLUSTER MEET 172.17.0.4 6379
CLUSTER MEET 172.17.0.5 6379
CLUSTER MEET 172.17.0.6 6379
CLUSTER MEET 172.17.0.7 6379
```

- 查看节点

`cluster nodes`

```shell
127.0.0.1:7000> cluster nodes
5c15089e26a15b67a2846f7c3e31ab6c761a1158 172.17.0.3:6379@16379 master - 0 1577099624653 1 connected
155064f4e6da923de4f58172ee018417d6942dca 172.17.0.7:6379@16379 master - 0 1577099623644 5 connected
b7e7e51cd219e25139fb0dfeae96d658bfcfc70b 172.17.0.2:6379@16379 myself,master - 0 1577099624000 2 connected
e165755e4b9eeadbe0f85a0f5a38a3242cc13ecc 172.17.0.6:6379@16379 master - 0 1577099625664 0 connected
b77eeb3979d3f6efbaf8c368bd7989d9dccb39a4 172.17.0.5:6379@16379 master - 0 1577099622000 3 connected
df05c9c3f2f0a7ba39a7c7168cc73e4b9183c9d8 172.17.0.4:6379@16379 master - 0 1577099623000 4 connected
```

`cluster info`查看集群信息

```shell
127.0.0.1:7000> CLUSTER info
cluster_state:fail
cluster_slots_assigned:3568
cluster_slots_ok:3568
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:1
cluster_current_epoch:5
cluster_my_epoch:2
cluster_stats_messages_ping_sent:240
cluster_stats_messages_pong_sent:248
cluster_stats_messages_meet_sent:6
cluster_stats_messages_sent:494
cluster_stats_messages_ping_received:247
cluster_stats_messages_pong_received:246
cluster_stats_messages_meet_received:1
cluster_stats_messages_received:494
```

当前只是添加了节点，并没有分配槽点，没有分配主从关系，所以`cluster_state:fail`集群没有生效

配置槽点

添加脚本

```shell
mkdir docker-shell
cd docker-shell
touch addslots.sh
vim addslots.sh
```

复制以下配置

```shell
#!/bin/bash
#将0-5461的槽点配置在172.17.0.2:6379（宿主机127.0.0.1:7000）的redis上
n=0
for ((i=n;i<=5461;i++))
do
   /data/redis-4.0.1/src/redis-cli -h 127.0.0.1 -p 7000 -a 123456789  CLUSTER ADDSLOTS $i
done

#将5462-10922的槽点配置在172.17.0.3:6379（宿主机127.0.0.1:7001）的redis上
n=5462
for ((i=n;i<=10922;i++))
do
   /data/redis-4.0.1/src/redis-cli -h 127.0.0.1 -p 7001 -a 123456789  CLUSTER ADDSLOTS $i
done

#将10923-16383的槽点配置在172.17.0.4:6379（宿主机127.0.0.1:7002）的redis上
n=10923
for ((i=n;i<=16383;i++))
do
   /data/redis-4.0.1/src/redis-cli -h 127.0.0.1 -p 7002 -a 123456789  CLUSTER ADDSLOTS $i
done

```

执行脚本

```shell
sh addslots.sh
```

等待脚本执行结束，查看集群信息

```shell
172.17.0.3:6379> CLUSTER info
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:5
cluster_my_epoch:1
cluster_stats_messages_ping_sent:2071
cluster_stats_messages_pong_sent:2048
cluster_stats_messages_meet_sent:2
cluster_stats_messages_sent:4121
cluster_stats_messages_ping_received:2044
cluster_stats_messages_pong_received:2073
cluster_stats_messages_meet_received:4
cluster_stats_messages_received:4121
```

`cluster_state:ok`集群已经生效

## 6. 主从高可用

查看节点信息`cluster nodes`

```shell
127.0.0.1:7000> CLUSTER NODES
5c15089e26a15b67a2846f7c3e31ab6c761a1158 172.17.0.3:6379@16379 master - 0 1577100113730 1 connected 5462-10922
155064f4e6da923de4f58172ee018417d6942dca 172.17.0.7:6379@16379 master - 0 1577100113000 5 connected
b7e7e51cd219e25139fb0dfeae96d658bfcfc70b 172.17.0.2:6379@16379 myself,master - 0 1577100112000 2 connected 0-5461
e165755e4b9eeadbe0f85a0f5a38a3242cc13ecc 172.17.0.6:6379@16379 master - 0 1577100111717 0 connected
b77eeb3979d3f6efbaf8c368bd7989d9dccb39a4 172.17.0.5:6379@16379 master - 0 1577100112000 3 connected
df05c9c3f2f0a7ba39a7c7168cc73e4b9183c9d8 172.17.0.4:6379@16379 master - 0 1577100112000 4 connected 10923-16383
```

可见三主没有问题，槽点也都分配到redis服务中，集群状态没有问题，但是还有三个redis服务也是主节点，也没有分配槽点，也没有配置主从关联，一旦现有的三个redis服务其中一个出现问题，整个集群奇偶就失效了。

- 主从管理

- - 主从关系关联表

  |     主redis     | 主redis(宿主)  |     从redis     | 从redis(宿主)  |
  | :-------------: | :------------: | :-------------: | :------------: |
  | 172.17.0.2:6379 | 127.0.0.1:7000 | 172.17.0.5:6379 | 127.0.0.1:7003 |
  | 172.17.0.3:6379 | 127.0.0.1:7001 | 172.17.0.6:6379 | 127.0.0.1:7004 |
  | 172.17.0.4:6379 | 127.0.0.1:7002 | 172.17.0.7:6379 | 127.0.0.1:7005 |

- 编写关联脚本

```shell
touch addSlaveNodes.sh
vim addSlaveNodes.sh
```

粘贴一下内容

```shell
#CLUSTER REPLICATE后面跟的是172.17.0.4:6379的集群ID
/data/redis-4.0.1/src/redis-cli -h 127.0.0.1 -p 7003 -a 123456789 CLUSTER REPLICATE b7e7e51cd219e25139fb0dfeae96d658bfcfc70b
#CLUSTER REPLICATE后面跟的是172.17.0.5:6379的集群ID
/data/redis-4.0.1/src/redis-cli -h 127.0.0.1 -p 7004 -a 123456789 CLUSTER REPLICATE 5c15089e26a15b67a2846f7c3e31ab6c761a1158
#CLUSTER REPLICATE后面跟的是172.17.0.6:6379的集群ID
/data/redis-4.0.1/src/redis-cli -h 127.0.0.1 -p 7005 -a 123456789 CLUSTER REPLICATE df05c9c3f2f0a7ba39a7c7168cc73e4b9183c9d8
```

执行脚本，查看运行结果

```shell
[root@localhost redis-shell]# sh addSlaveNodes.sh 
OK
OK
OK
```

主从配置完成，查看节点信息`cluster nodes`

```shell
127.0.0.1:7000> cluster nodes
5c15089e26a15b67a2846f7c3e31ab6c761a1158 172.17.0.3:6379@16379 master - 0 1577111822429 1 connected 5462-10922
155064f4e6da923de4f58172ee018417d6942dca 172.17.0.7:6379@16379 slave df05c9c3f2f0a7ba39a7c7168cc73e4b9183c9d8 0 1577111821407 5 connected
b7e7e51cd219e25139fb0dfeae96d658bfcfc70b 172.17.0.2:6379@16379 myself,master - 0 1577111819000 2 connected 0-5461
e165755e4b9eeadbe0f85a0f5a38a3242cc13ecc 172.17.0.6:6379@16379 slave 5c15089e26a15b67a2846f7c3e31ab6c761a1158 0 1577111819000 5 connected
b77eeb3979d3f6efbaf8c368bd7989d9dccb39a4 172.17.0.5:6379@16379 slave b7e7e51cd219e25139fb0dfeae96d658bfcfc70b 0 1577111821000 5 connected
df05c9c3f2f0a7ba39a7c7168cc73e4b9183c9d8 172.17.0.4:6379@16379 master - 0 1577111820386 4 connected 10923-16383
```

主从关系已经配置成功，三主三从。

- 读写测试

```shell
[root@localhost data]# /data/redis-4.0.1/src/redis-cli -c -p 7000 -a 123456789
127.0.0.1:7000> set 123 123
-> Redirected to slot [5970] located at 172.17.0.3:6379
OK
172.17.0.3:6379> get 123
"123"
172.17.0.3:6379> del 123
(integer) 1
172.17.0.3:6379> get 123
(nil)
172.17.0.3:6379> 
```

搭建完成。