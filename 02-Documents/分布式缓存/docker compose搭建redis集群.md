# 1.创建目录及文件

```shell
# 创建目录
mkdir -p /home/tomato/docker/redis-cluster
# 切换至指定目录
cd /home/tomato/docker/redis-cluster
# 编写 redis-cluster.tmpl 文件
vim redis-cluster.tmpl
```

# 2.编写配置文件

**`192.168.209.21` 机器的 `redis-cluster.tmpl` 文件内容如下**

```properties
port ${PORT}
requirepass 1234
masterauth 1234
protected-mode no
daemonize no
appendonly yes
auto-aof-rewrite-percentage 20
auto-aof-rewrite-min-size 1mb
aof-use-rdb-preamble yes
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.209.21
cluster-announce-port ${PORT}
cluster-announce-bus-port 1${PORT}
```

**192.168.209.22 机器的 redis-cluster.tmpl 文件内容如下**

```properties
port ${PORT}
requirepass 1234
masterauth 1234
protected-mode no
daemonize no
appendonly yes
auto-aof-rewrite-min-size 1mb
auto-aof-rewrite-percentage 20
aof-use-rdb-preamble yes
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.209.22
cluster-announce-port ${PORT}
cluster-announce-bus-port 1${PORT}
```

**192.168.209.23机器的 redis-cluster.tmpl 文件内容如下**

```properties
port ${PORT}
requirepass 1234
masterauth 1234
protected-mode no
daemonize no
appendonly yes
auto-aof-rewrite-min-size 1mb
auto-aof-rewrite-percentage 20
aof-use-rdb-preamble yes
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 15000
cluster-announce-ip 192.168.209.23
cluster-announce-port ${PORT}
cluster-announce-bus-port 1${PORT}
```

**每个 Redis 集群节点都需要打开两个 TCP 连接。一个用于为客户端提供服务的正常 Redis TCP 端口，例如 6379。还有一个基于 6379 端口加 10000 的端口，比如 16379。**

　　**第二个端口用于集群总线，这是一个使用二进制协议的节点到节点通信通道。节点使用集群总线进行故障检测、配置更新、故障转移授权等等。客户端永远不要尝试与集群总线端口通信，与正常的 Redis 命令端口通信即可，但是请确保防火墙中的这两个端口都已经打开，否则 Redis 集群节点将无法通信。**



**`192.168.209.21` 机器`redis-cluster` 目录下执行如下命令**

```shell
for port in `seq 6371 6373`; do \
  mkdir -p ${port}/conf \
  && PORT=${port} envsubst < redis-cluster.tmpl > ${port}/conf/redis.conf \
  && mkdir -p ${port}/data;\
done
```

**192.168.209.22 机器redis-cluster目录下执行如下命令**

```shell
for port in `seq 6374 6376`; do \
  mkdir -p ${port}/conf \
  && PORT=${port} envsubst < redis-cluster.tmpl > ${port}/conf/redis.conf \
  && mkdir -p ${port}/data;\
done
```

**192.168.209.23 机器redis-cluster 目录下执行如下命令**

```shell
for port in `seq 6377 6379`; do \
  mkdir -p ${port}/conf \
  && PORT=${port} envsubst < redis-cluster.tmpl > ${port}/conf/redis.conf \
  && mkdir -p ${port}/data;\
done
```



**`192.168.209.21` 机器** **编写docker-compose.yml文件**

redis-cluster目录下创建 `docker-compose.yml` 文件并编辑

```yaml
# 描述 Compose 文件的版本信息
version: "3.9"

# 定义服务，可以多个
services:
  redis-6371: # 服务名称
    image: redis # 创建容器时所需的镜像
    container_name: redis-6371 # 容器名称
    restart: always # 容器总是重新启动
    network_mode: "host" # host 网络模式
    volumes: # 数据卷，目录挂载
      - /home/tomato/docker/redis-cluster/6371/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6371/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf # 覆盖容器启动后默认执行的命令

  redis-6372:
    image: redis
    container_name: redis-6372
    network_mode: "host"
    volumes:
      - /home/tomato/docker/redis-cluster/6372/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6372/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf

  redis-6373:
    image: redis
    container_name: redis-6373
    network_mode: "host"
    volumes:
      - /home/tomato/docker/redis-cluster/6373/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6373/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf

```



**192.168.209.22 机器** **编写docker-compose.yml文件**

redis-cluster目录下创建 `docker-compose.yml` 文件并编辑

```yaml
# 描述 Compose 文件的版本信息
version: "3.9"

# 定义服务，可以多个
services:
  redis-6374: # 服务名称
    image: redis # 创建容器时所需的镜像
    container_name: redis-6374 # 容器名称
    restart: always # 容器总是重新启动
    network_mode: "host" # host 网络模式
    volumes: # 数据卷，目录挂载
      - /home/tomato/docker/redis-cluster/6374/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6374/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf # 覆盖容器启动后默认执行的命令

  redis-6375:
    image: redis
    container_name: redis-6375
    network_mode: "host"
    volumes:
      - /home/tomato/docker/redis-cluster/6375/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6375/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf

  redis-6376:
    image: redis
    container_name: redis-6376
    network_mode: "host"
    volumes:
      - /home/tomato/docker/redis-cluster/6376/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6376/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf
```

**192.168.209.23 机器** **编写docker-compose.yml文件**

redis-cluster目录下创建 `docker-compose.yml` 文件并编辑

```yaml
# 描述 Compose 文件的版本信息
version: "3.9"

# 定义服务，可以多个
services:
  redis-6377: # 服务名称
    image: redis # 创建容器时所需的镜像
    container_name: redis-6377 # 容器名称
    restart: always # 容器总是重新启动
    network_mode: "host" # host 网络模式
    volumes: # 数据卷，目录挂载
      - /home/tomato/docker/redis-cluster/6377/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6377/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf # 覆盖容器启动后默认执行的命令

  redis-6378:
    image: redis
    container_name: redis-6378
    network_mode: "host"
    volumes:
      - /home/tomato/docker/redis-cluster/6378/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6378/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf

  redis-6379:
    image: redis
    container_name: redis-6379
    network_mode: "host"
    volumes:
      - /home/tomato/docker/redis-cluster/6379/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /home/tomato/docker/redis-cluster/6379/data:/data
    command: redis-server /usr/local/etc/redis/redis.conf
```

**创建并启动三台服务器所有容器**

分别在 `192.168.209.21` 、 `192.168.209.22`、192.168.209.23 机器的 `/home/tomato/docker/redis-cluster` 目录下执行以下命令：

```shell
sudo docker-compose -f docker-compose.yml up -d
```

# 3.创建redis集群

请先确保你的两台机器可以互相通信，然后随便进入一个容器节点，并进入 `/usr/local/bin/` 目录：

```shell
# 进入容器
sudo docker exec -it redis-6371 /bin/bash
# 切换至指定目录
cd /usr/local/bin/
```

通过以下命令实现 Redis Cluster 集群的创建

组成三主六从的redis集群 ,每个主机对应两个从机

```shell
redis-cli -a 1234 --cluster create 192.168.209.21:6371 192.168.209.21:6372 192.168.209.21:6373 192.168.209.22:6374 192.168.209.22:6375 192.168.209.22:6376 192.168.209.23:6377 192.168.209.23:6378 192.168.209.23:6379 --cluster-replicas 2
```

# 4.检查集群状态

```shell
redis-cli -a 1234 --cluster check 192.168.209.21:6371
```

**查看集群信息和节点信息**

```shell
# 连接至集群某个节点
redis-cli -c -a 1234 -h 192.168.209.21 -p 6371
# 查看集群信息
cluster info
# 查看集群结点信息
cluster nodes
```

**解决docker容器时间与宿主机时间不一致问题**

**进入容器执行**

```shell
#容器中修改下/etc/localtime文件的名称，避免冲突。
mv /etc/localtime /etc/localtime_bak
cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
```

# 5.删除节点

**删除从节点**

**用del-node删除从节点，指定需要删除节点的ip和端口，以及节点id**

```shell
redis-cli -a 1234 --cluster del-node 192.168.209.21:6376 a1cfe35722d151cf70585cee21275565393c0956
```

再次查看集群状态，这个slave节点已经移除，并且该节点的redis服务也已被停止

**删除主节点**

主节点的里面是有分配了hash槽的，所以我们这里必须
先把主节点里的hash槽放入到其他的可用主节点中去，然后再进行移除节点操作，不然会出现数据丢失问题(目前只能把master的数据迁移到一个节点上，暂时做不了平均分配功能)，执行命令如下

```shell
redis-cli -a 1234 --cluster reshard 192.168.209.22:6375
```

**输出如下**：

```shell
... ...
How many slots do you want to move (from 1 to 16384)? 600
What is the receiving node ID? dfca1388f124dec92f394a7cc85cf98cfa02f86f
(ps:这里是需要把数据移动到哪？8001的主节点id)
Please enter all the source node IDs.
Type 'all' to use all the nodes as source nodes for the hash slots.
Type 'done' once you entered all the source nodes IDs.
Source node 1:2728a594a0498e98e4b83a537e19f9a0a3790f38
(ps:这里是需要数据源，也就是我们的8007节点id)
Source node 2:done
(ps:这里直接输入done 开始生成迁移计划)
... ...
Do you want to proceed with the proposed reshard plan (yes/no)? Yes
(ps:这里输入yes开始迁移)
```

**检查集群状态，可以看的6375主节点下面已经没有任何hash槽**

```shell
redis-cli -a 1234 --cluster check 192.168.209.22:6374
```

```shell
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 192.168.209.22:6374)
M: 50b690476ef37321a379883bf896114d990cdf87 192.168.209.22:6374
   slots:[0-10922] (10923 slots) master
   1 additional replica(s)
S: c9e19d71ac7d14a90d24e908f9627d4479dd1f91 192.168.209.23:6377
   slots: (0 slots) slave
   replicates a43216fdc83283b91298e747af1dc485610a084a
M: 37710a476f4c6d3e6a1822ad56234a9aae0d1ebb 192.168.209.22:6375
   slots: (0 slots) master
M: a43216fdc83283b91298e747af1dc485610a084a 192.168.209.22:6376
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: a75f03319c1a9f48daeda16595c7ca52eae15d66 192.168.209.21:6371
   slots: (0 slots) slave
   replicates 50b690476ef37321a379883bf896114d990cdf87
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

**最后我们直接使用del-node命令删除6375主节点即可**

```shell
redis-cli -a 1234 --cluster del-node 192.168.209.22:6375 37710a476f4c6d3e6a1822ad56234a9aae0d1ebb
```

# 6.添加节点

**添加主节点**

**使用add-node命令新增一个主节点6371(master)，前面的ip:port为新增节点，后面的ip:port为已知存在节点，看到日志最后有"[OK]**

**New node added correctly"提示代表新节点加入成功**

```shell
redis-cli -a 1234 --cluster add-node 192.168.209.21:6371 192.168.209.22:6374
```

**查看集群状态**

```shell
redis-cli -a 1234 --cluster check 192.168.209.22:6374
```

**注意：当添加节点成功以后，新增的节点不会有任何数据，因为它还没有分配任何的slot(hash槽)，我们需要为新节点手工分配hash槽**

**使用redis-cli命令为6371分配hash槽，找到集群中的任意一个主节点，对其进行重新分片工作。**

```shell
redis-cli -a 1234 --cluster reshard 192.168.209.22:6374
```

**添加从节点**

```shell
redis-cli -a 1234 --cluster add-node 192.168.209.21:6372 192.168.209.21:6371
redis-cli -a 1234 --cluster add-node 192.168.209.21:6373 192.168.209.21:6371

redis-cli -a 1234 --cluster add-node 192.168.209.22:6375 192.168.209.21:6371
redis-cli -a 1234 --cluster add-node 192.168.209.22:6376 192.168.209.21:6371

redis-cli -a 1234 --cluster add-node 192.168.209.23:6378 192.168.209.21:6371
redis-cli -a 1234 --cluster add-node 192.168.209.23:6379 192.168.209.21:6371
```

这样添加后，还是master节点，没有被分配任何的hash槽。

我们需要执行replicate命令来指定当前节点(从节点)的主节点id为哪个,首先需要连接新加的6372节点的客户端，然后使用集群命令进行

操作，把当前的6372(slave)节点指定到一个主节点下(这里使用之前创建的6371主节点)

```shell
redis-cli -a 1234 -c -h 192.168.209.21 -p 6372 

cluster replicate 50b690476ef37321a379883bf896114d990cdf87 #后面这串id为6371的节点id
```

