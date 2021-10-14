[TOC]

# 1.docker搭建elasticsearch服务

## 1.1.创建elasticsearch容器

```shell
docker run -p 9200:9200 -p 9300:9300 --name elasticsearch \
-e "discovery.type=single-node" \
-e "cluster.name=elasticsearch" \
-e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
-v /home/nerotomato/Develop/docker/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-v /home/nerotomato/Develop/docker/elasticsearch/data:/usr/share/elasticsearch/data \
-d elasticsearch:7.14.0
```

**修该路径/home/nerotomato/Develop/docker/elasticsearch/data权限为777，否则es会启动失败**

```shell
sudo chmod 777 /home/nerotomato/Develop/docker/elasticsearch/data
```

**安装中文分词器IKAnalyzer，并重新启动**

```shell
mkdir /home/nerotomato/Develop/docker/elasticsearch/plugins/ik
docker exec -it elasticsearch /bin/bash
#此命令需要在容器中运行
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.14.0/elasticsearch-analysis-ik-7.14.0.zip
docker restart elasticsearch
```

**也可以直接将elasticsearch-analysis-ik-7.14.0.zip解压到/home/nerotomato/Develop/docker/elasticsearch/plugins/ik**

```shell
mkdir /home/nerotomato/Develop/docker/elasticsearch/plugins/ik
unzip elasticsearch-analysis-ik-7.14.0.zip -d /home/nerotomato/Develop/docker/elasticsearch/plugins/ik
docker restart elasticsearch
```

# 2.docker搭建Kibana服务

## 2.1.创建kibana容器

```shell
docker run --name kibana -p 5601:5601 \
--link elasticsearch:es \
-e "elasticsearch.hosts=http://es:9200" \
-d kibana:7.14.0
```

## 2.2.**在Dev Tools执行查询语句**

**浏览器访问http://localhost:5601/**

**查看集群健康状态**

```sql
GET /_cat/health?v
```

**查看节点状态**

```sql
GET /_cat/nodes?v
```

**查看所有索引信息**

```sql
GET /_cat/indices?v
```

**创建索引并查看**

```sql
PUT /customer
GET /_cat/indices?v
```

**删除索引并查看**

```sql
DELETE /customer
GET /_cat/indices?v
```

**在索引中添加文档**

```sql
PUT /customer/doc/1
{
  "name": "John Doe"
}
```

**查看索引中的文档**

```sql
GET /customer/doc/1
```

**修改索引中的文档**

```sql
POST /customer/doc/1/_update
{
  "doc": { "name": "Jane Doe" }
}
```

**删除索引中的文档**

```sql
DELETE /customer/doc/1
```

