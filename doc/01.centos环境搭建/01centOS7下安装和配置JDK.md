# centOS7下安装和配置JDK

### 1.检测当前系统是否已安装JDK

```
java -version
```

### 2.卸载openJDK

#### 2.1.检测jdk安装包

```
rpm -qa|grep java
```

#### 2.2.卸载openJDK

```
yum remove *openjdk*
```

#### 2.3.查看卸载情况

```
rpm -qa|grep java
```

### 3.下载JDK

#### 3.1.查看系统是32位还是64位

```
getconfig LONG_BIT
```

#### 3.2.下载JDK

```
下载网址：http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
```

### 4.安装JDK

4.1.新建文件夹/usr/local/java,进入java文件夹

```
mkdir -p /usr/local/java
cd /usr/local/java
```

4.2.利用WinSCP工具，将下载的压缩包上传到新建的java目录下面,解压文件

```
# 文件解压缩
tar zxvf jdk-8u152-linux-x64.tar.gz
# 删除压缩文件
rm -rf jdk-8u152-linux-x64.tar.gz
```

4.3.环境变量配置

```
vim /etc/profile
```

配置文件底部添加：

```
export JAVA_HOME=/usr/local/java/jdk1.8.0_131
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```

保存后，执行：

```
source /etc/profile
```

检查是否生效：

```
java -version
```

