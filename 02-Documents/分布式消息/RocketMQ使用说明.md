## 1.本地启动

**注意配置JAVA_HOME环境变量**

修改mac用户目录下配置文件.zshrc 

```properties
export JAVA_HOME=/Users/nerotomato/Library/Java/JavaVirtualMachines/azul-1.8.0_312/Contents/Home
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=$(dirname $0)/..
export CLASSPATH=.:${BASE_DIR}/conf:${BASE_DIR}/lib/*:${CLASSPATH}
```

**Start NameServer**

```shell
cd rocketmq-4.9.3/bin
### start Name Server
nohup sh mqnamesrv &

### check whether Name Server is successfully started
tail -f ~/logs/rocketmqlogs/namesrv.log
The Name Server boot success...
```

**Start Broker**

```shell
### start Broker
nohup sh mqbroker -n localhost:9876 &
### check whether Broker is successfully started, eg: Broker's IP is 192.168.1.2, Broker's name is broker-a
tail -f ~/logs/rocketmqlogs/broker.log
```

RocketMQ默认的虚拟机内存较大，启动Broker如果因为内存不足失败，需要编辑如下两个配置文件，修改JVM内存大小

```shell
# 编辑runbroker.sh和runserver.sh修改默认JVM大小
vi runbroker.sh
vi runserver.sh
```

**测试RocketMQ**

发送消息

```sh
# 1.设置环境变量
export NAMESRV_ADDR=localhost:9876
cd /Users/nerotomato/Develop/software/mq/rocketmq-4.9.3/bin
# 2.使用安装包的Demo发送消息
sh tools.sh org.apache.rocketmq.example.quickstart.Producer
```

接收消息

```shell
# 1.设置环境变量
export NAMESRV_ADDR=localhost:9876
cd /Users/nerotomato/Develop/software/mq/rocketmq-4.9.3/bin
# 2.接收消息
sh tools.sh org.apache.rocketmq.example.quickstart.Consumer
```

**关闭RocketMQ**

```shell
# 1.关闭NameServer
cd /Users/nerotomato/Develop/software/mq/rocketmq-4.9.3/bin
sh mqshutdown namesrv
# 2.关闭Broker
sh mqshutdown broker
```





## 2.docker方式启动

**1) Start NameServer**

```shell
docker run -it --net=host apache/rocketmq ./mqnamesrv
```

**2) Start Broker**

```shell
docker run -it --net=host --mount source=/tmp/store,target=/home/rocketmq/store apache/rocketmq ./mqbroker -n localhost:9876
```

