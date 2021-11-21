# 1.下载Docker compose

**docker compose版本号需要与docker版本号对应**

```shell
curl -L https://get.daocloud.io/docker/compose/releases/download/1.29.2/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
```

**修改该文件的权限为可执行**

```shell
chmod +x /usr/local/bin/docker-compose
```

**查看是否安装成功**

```shell
docker-compose --version
```

**注意**：如果`docker-compose`安装后命令失败，请检查您的路径。您还可以`/usr/bin`在路径中创建指向或任何其他目录的符号链接。

例如：

```shell
$ sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```



# 2.docker compose搭建elasticsearch集群、kibana、logstash服务

**编写docker-compose.yml文件**

**注意：此处为同一台机器部署多个节点的集群，需要修个每个节点的宿主机端口号，不能相同**

**同一机器部署多个节点，以下配置可以使集群达到健康green状态**

**复制以下配置到服务器的时候，注意每行内容的缩进是否改变，如果改变，启动会报错，需要手动修改一下缩进内容**

```yaml
version: '3.9'
services:
  #指定服务名称,elasticsearch集群服务
  es01:
  	# 指定服务使用的镜像
    #image: docker.elastic.co/elasticsearch/elasticsearch:7.4.1
    image: elasticsearch:7.14.0
    # 指定容器名称
    container_name: es01
    # 指定容器的环境变量
    environment:
      - node.name=es01
      - node.master=true
   	  - node.data=true
      - cluster.name=elasticsearch-cluster
      - discovery.seed_hosts=es02,es03
      #指定初始化时的主节点列表，用于而第一次投票选举，node.master=true的才可以配置在这里
      #- cluster.initial_master_nodes=es01,es02,es03
      - cluster.initial_master_nodes=es01
      - bootstrap.memory_lock=true
      - http.cors.enabled=true    #是否支持跨域访问资源
      - http.cors.allow-origin=*  #允许访问资源的类型
      #- "discovery.zen.ping.unicast.hosts=es01,es02,es03" #该配置与discovery.seed_hosts配置其中一个就行
   	  - "discovery.zen.minimum_master_nodes=2"
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - TZ=Asia/Shanghai
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - /home/nerotomato/Develop/docker/elasticsearch-cluster/es01/data:/usr/share/elasticsearch/data
      - /home/nerotomato/Develop/docker/elasticsearch-cluster/es01/plugins:/usr/share/elasticsearch/plugins
    ports:
      - 9201:9200
      - 9301:9300
    networks:
      - elastic
  es02:
    image: elasticsearch:7.14.0
    container_name: es02
    environment:
      - node.name=es02
      - node.master=false
   	  - node.data=true
      - cluster.name=elasticsearch-cluster
      - discovery.seed_hosts=es01,es03
      #- cluster.initial_master_nodes=es01,es02,es03
      - cluster.initial_master_nodes=es01
      - bootstrap.memory_lock=true
      - http.cors.enabled=true
      - http.cors.allow-origin=*
      #- "discovery.zen.ping.unicast.hosts=es01,es02,es03"
   	  - "discovery.zen.minimum_master_nodes=2"
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - TZ=Asia/Shanghai
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - /home/nerotomato/Develop/docker/elasticsearch-cluster/es02/data:/usr/share/elasticsearch/data
      - /home/nerotomato/Develop/docker/elasticsearch-cluster/es02/plugins:/usr/share/elasticsearch/plugins
    ports:
      - 9202:9200
      - 9302:9300
    networks:
      - elastic
  es03:
    image: elasticsearch:7.14.0
    container_name: es03
    environment:
      - node.name=es03
      - node.master=false
   	  - node.data=true
      - cluster.name=elasticsearch-cluster
      - discovery.seed_hosts=es01,es02
      #- cluster.initial_master_nodes=es01,es02,es03
      - cluster.initial_master_nodes=es01
      - bootstrap.memory_lock=true
      - http.cors.enabled=true
      - http.cors.allow-origin=*
      #- "discovery.zen.ping.unicast.hosts=es01,es02,es03"
   	  - "discovery.zen.minimum_master_nodes=2"
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - TZ=Asia/Shanghai
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - /home/nerotomato/Develop/docker/elasticsearch-cluster/es03/data:/usr/share/elasticsearch/data
      - /home/nerotomato/Develop/docker/elasticsearch-cluster/es03/plugins:/usr/share/elasticsearch/plugins
    ports:
      - 9203:9200
      - 9303:9300
    networks:
      - elastic
  #kibana服务
  kibana:
    image: kibana:7.14.0
    container_name: kibana
    links:
      - es01:es01 #可以用es01这个域名访问elasticsearch服务
      - es02:es02
      - es03:es03
    depends_on:
      - es01 #kibana在elasticsearch集群启动之后再启动
      - es02
      - es03
    volumes:
      #挂载kibana的配置文件
      - /home/nerotomato/Develop/docker/kibana/kibana.yml:/usr/share/kibana/config/kibana.yml
    environment:
      - TZ=Asia/Shanghai
      #注意这里要写容器内的端口，因为是容器与容器之间通信
      - "elasticsearch.hosts=http://es01:9200,http://es02:9200,http://es03:9200" #设置访问elasticsearch的地址
    ports:
      - 5601:5601
    networks:
      - elastic
  #logstash服务
  logstash:
    image: logstash:7.14.0
    container_name: logstash
    environment:
      - TZ=Asia/Shanghai
    volumes:
      #挂载logstash的配置文件
      - /home/nerotomato/Develop/docker/logstash/logstash.conf:/usr/share/logstash/pipeline/logstash.conf
      - /home/nerotomato/Develop/docker/logstash/logstash.yml:/usr/share/logstash/config/logstash.yml
    depends_on:
      - es01 #logstash在elasticsearch集群启动之后再启动
      - es02
      - es03
    links:
      - es01:es01 #可以用es01这个域名访问elasticsearch服务
      - es02:es02
      - es03:es03
    ports:
      - 4560:4560
      - 4561:4561
      - 4562:4562
      - 4563:4563
    networks:
      - elastic
networks:
  elastic:
    driver: bridge
```

**以上参考的是https://stackoverflow.com/questions/13477303/multiple-nodes-in-elasticsearch/13478781#建议的配置**



**logstash配置文件**

**logstash.yml**

```yaml
http.host: "0.0.0.0"
xpack.monitoring.enabled: true
xpack.monitoring.elasticsearch.hosts: [ "http://es01:9200","http://es02:9200","http://es03:9200" ]

```

**logstash.conf**

```properties
input {
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 4560
    codec => json_lines
    type => "debug"
  }
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 4561
    codec => json_lines
    type => "error"
  }
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 4562
    codec => json_lines
    type => "business"
  }
  tcp {
    mode => "server"
    host => "0.0.0.0"
    port => 4563
    codec => json_lines
    type => "record"
  }
}
filter{
  if [type] == "record" {
    mutate {
      remove_field => "port"
      remove_field => "host"
      remove_field => "@version"
    }
    json {
      source => "message"
      remove_field => ["message"]
    }
  }
}
output {
  elasticsearch {
  	#注意这里要写容器内的端口，因为是容器与容器之间通信
    hosts => ["es01:9200","es02:9200","es03:9200"]
    index => "mall-%{type}-%{+YYYY.MM.dd}"
  }
}

```

**kibana配置文件**

```yaml
server.host: "0"
server.shutdownTimeout: "5s"
#注意这里要写容器内的端口，因为是容器与容器之间通信
elasticsearch.hosts: [ "http://es01:9200","http://es02:9200","http://es03:9200" ]
monitoring.ui.container.elasticsearch.enabled: true
```

**构建、创建、启动相关容器**

```shell
# -d表示在后台运行
docker-compose up -d
```

**指定文件启动**

```shell
docker-compose -f docker-compose.yml up -d
```

**停止所有相关容器**

```shell
docker-compose stop
```

**列出所有容器信息**

```shell
docker-compose ps
```

**修改 /etc/sysctl.conf 文件**

**报错虚拟内存太小：max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]**

**调整虚拟内存大小**

```shell
echo "vm.max_map_count=262144" >> /etc/sysctl.conf
sysctl -p  #使修改立即生效
```

**使用elasticsearch-head插件访问集群**

```shell
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
npm run start
open http://localhost:9100/
```

