# 1.docker-compose部署ELK、Skywalking、Redis、Nacos、Nginx等应用

```yaml
version: '3.9'
services:
  nginx:
    image: nginx:1.21.3
    container_name: nginx
    volumes:
      - /home/nerotomato/Develop/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf #配置文件挂载
      - /home/nerotomato/Develop/docker/nginx/html:/usr/share/nginx/html #静态资源根目录挂载
      - /home/nerotomato/Develop/docker/nginx/log:/var/log/nginx #日志文件挂载
    ports:
      - 80:80
  #指定服务名称,elasticsearch集群服务
  es01:
    # 指定服务使用的镜像
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
  #logstash服务
  logstash:
    image: logstash:7.14.0
    container_name: logstash
    environment:
      - TZ=Asia/Shanghai
    volumes:
      #挂载logstash的配置文件
      - /home/nerotomato/Develop/docker/logstash/data/:/usr/data/
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
  #skywalking-ui服务
  skywalking-ui:
    image: apache/skywalking-ui:8.5.0
    container_name: skywalking-ui
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
  #redis单实例
  redis:
    image: redis:latest
    container_name: redis
    command: redis-server --appendonly yes
    volumes:
      - /home/nerotomato/Develop/docker/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/nerotomato/Develop/docker/redis/data:/data #数据文件挂载
    ports:
      - 6379:6379
  #nacos注册中心服务
  nacos-registry:
    image: nacos/nacos-server:latest
    container_name: nacos-registry
    environment:
      - "MODE=standalone"
    volumes:
      - /home/nerotomato/Develop/docker/nacos/data:/home/nacos/data
      - /home/nerotomato/Develop/docker/nacos/logs:/home/nacos/logs
    ports:
      - 8848:8848
networks:
  default:
    external: #使用外部自定义的桥接网络 my-bridge
      name: my-bridge
        #networks:
        #  elastic:
        #driver: bridge
```

