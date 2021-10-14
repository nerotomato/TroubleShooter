# 1.下载镜像

这里由于我使用的是elasticsearch 7.14版本，所以下载的skywalking镜像要选择支持es7版本的

```shell
docker pull apache/skywalking-oap-server:8.7.0-es7
docker pull apache/skywalking-ui:8.7.0
```

# 2.编写docker-compose.yml文件

**注意：skywalking镜像oap镜像使用的是8.5-es7版本，ui镜像也是8.5.0版本。如果用8.7版本的skywalking-oap和skywalking-ui镜像会有问题，oap服务需要重启多次才能连接ES，查资料说要等待es服务ready状态后才能连接，而ui服务却一直报DNS解析错误。这里我使用8.5版本的skywalking oap和ui镜像没有问题。**

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
  #skywalking-oap服务
  skywalking-oap:
  	image: apache/skywalking-oap-server:8.5.0-es7
    container_name: skywalking-oap
    restart: always #skywalking-oap一开始可能会连不上ES集群，需要重启等ES服务ready才能连接成功
    environment:
      - TZ=Asia/Shanghai
      - SW_CLUSTER=standalone
      - SW_STORAGE=elasticsearch7
      - SW_STORAGE_ES_CLUSTER_NODES=es01:9200,es02:9200,es03:9200
    #volumes:
      #挂载skywalking的配置文件
      #- /home/nerotomato/Develop/docker/skywalking/config/application.yml:/skywalking/config/application.yml
    depends_on:
      - es01 #在elasticsearch集群启动之后再启动
      - es02
      - es03
    links:
      - es01:es01 #可以用es01这个域名访问elasticsearch服务
      - es02:es02
      - es03:es03
    ports:
      - 11800:11800
      - 12800:12800
    networks:
      - elastic
  #skywalking-ui服务
  skywalking-ui:
  	image: apache/skywalking-ui:8.5.0
    container_name: skywalking-ui
    restart: always
    environment:
      - TZ=Asia/Shanghai
      - SW_OAP_ADDRESS=oap:12800
    #volumes:
      #挂载skywalking-ui的配置文件
      #- /home/nerotomato/Develop/docker/skywalking/config/webapp.yml:/skywalking/webapp/webapp.yml
    depends_on:
      - skywalking-oap
    links:
      - skywalking-oap:oap
    ports:
      - 8088:8080
    networks:
      - elastic
networks:
  elastic:
    driver: bridge
```

# 3.skywalking监控java应用

**java应用添加启动参数**

```shell
-javaagent:/home/nerotomato/Develop/java_develop/microservice/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar
```

**配置skyWalking环境变量(Enviroement Variables)**

```shell
SW_AGENT_NAME=ProductService;SW_AGENT_CONTROLLER_BACKEND_SERVICES=127.0.0.1:11800
```

