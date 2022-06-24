**1.replaction异步复制方式**
**#主数据库**
**windows下创建**

```shell
docker run --restart=always --privileged=true ^
 -v D:/DockerDesktop/dockerContainer/mysql-master/mysql-files:/var/lib/mysql-files ^
 -v D:/DockerDesktop/dockerContainer/mysql-master/log:/var/log/mysql ^
 -v D:/DockerDesktop/dockerContainer/mysql-master/data:/var/lib/mysql ^
 -v D:/DockerDesktop/dockerContainer/mysql-master/my.cnf:/etc/mysql/my.cnf ^
 -v D:/DockerDesktop/dockerContainer/mysql-master/conf.d:/etc/mysql/conf.d ^
 -e TZ=Asia/Shanghai ^
 --net my-bridge ^
 --ip 172.18.0.3 ^
 -p 3306:3306 --name mysql-master -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```

**linux环境创建**

```shell
docker run --restart=always --privileged=true \
 -v /home/nerotomato/Develop/docker/mysql-master/mysql-files:/var/lib/mysql-files \
 -v /home/nerotomato/Develop/docker/mysql-master/log:/var/log/mysql \
 -v /home/nerotomato/Develop/docker/mysql-master/data:/var/lib/mysql \
 -v /home/nerotomato/Develop/docker/mysql-master/my.cnf:/etc/mysql/my.cnf \
 -v /home/nerotomato/Develop/docker/mysql-master/conf.d:/etc/mysql/conf.d \
 -e TZ=Asia/Shanghai \
 --net my-bridge \
 --ip 172.18.0.3 \
 -p 3306:3306 --name mysql-master -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```

Mac环境创建

```shell
docker run --restart=always --privileged=true \
 -v /Users/nerotomato/Develop/docker/mysql-server/mysql-files:/var/lib/mysql-files \
 -v /Users/nerotomato/Develop/docker/mysql-server/log:/var/log/mysql \
 -v /Users/nerotomato/Develop/docker/mysql-server/data:/var/lib/mysql \
 -v /Users/nerotomato/Develop/docker/mysql-server/my.cnf:/etc/mysql/my.cnf \
 -v /Users/nerotomato/Develop/docker/mysql-server/conf.d:/etc/mysql/conf.d \
 -e TZ=Asia/Shanghai \
 -p 3306:3306 --name mysql-server -e MYSQL_ROOT_PASSWORD=123456 -d mysql/mysql-server:8.0.27
```

**配置文件my.cnf**

```properties
[mysqld]
server-id=1
log-bin=mysql-bin
binlog-format=mixed  #二进制日志格式
sync-binlog=1        #确保binlog日志写入后与硬盘同步
expire_logs_days=30  #自动清理30天前的日志文件
skip_name_resolve=ON #跳过反向域名解析
```

**#从数据库**

**windows下创建**

```shell
docker run --restart=always --privileged=true ^
-v D:/DockerDesktop/dockerContainer/mysql-slave/mysql-files:/var/lib/mysql-files ^
-v D:/DockerDesktop/dockerContainer/mysql-slave/log:/var/log/mysql ^
-v D:/DockerDesktop/dockerContainer/mysql-slave/data:/var/lib/mysql ^
-v D:/DockerDesktop/dockerContainer/mysql-slave/my.cnf:/etc/mysql/my.cnf ^
-v D:/DockerDesktop/dockerContainer/mysql-slave/conf.d:/etc/mysql/conf.d ^
-e TZ=Asia/Shanghai ^
--net my-bridge ^
--ip 172.18.0.4 ^
-p 3307:3306 --name mysql-slave -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```

**linux环境创建**

```shell
docker run --restart=always --privileged=true \
-v /home/nerotomato/Develop/docker/mysql-slave/mysql-files:/var/lib/mysql-files \
-v /home/nerotomato/Develop/docker/mysql-slave/log:/var/log/mysql \
-v /home/nerotomato/Develop/docker/mysql-slave/data:/var/lib/mysql \
-v /home/nerotomato/Develop/docker/mysql-slave/my.cnf:/etc/mysql/my.cnf \
-v /home/nerotomato/Develop/docker/mysql-slave/conf.d:/etc/mysql/conf.d \
-e TZ=Asia/Shanghai \
--net my-bridge \
--ip 172.18.0.4 \
-p 3307:3306 --name mysql-slave -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```



#修改容器中/etc/mysql/my.cnf 文件权限为644，否则mysql会忽略该文件。因为如果是权限777，任何一个用户都可以改my.cnf，存在很大的安全隐患。

#开启root用户远程连接的权限

```sql
update user set host='%' where user='root';
```

#登录主库，创建用户

```sql
CREATE USER 'reader'@'%' IDENTIFIED BY '123456';
```

#授权

```sql
GRANT REPLICATION SLAVE,REPLICATION CLIENT ON *.* TO 'reader'@'%';
```

#授予用户某个数据库的权限

```shell
GRANT ALL PRIVILEGES ON `mall`.* TO 'reader'@'%';
```

#mysql8.0需要修改plugin为mysql_native_password

```sql
ALTER USER 'reader'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
```

#刷新

```sql
FLUSH PRIVILEGES;
```

#查看主库状态

```sql
show master status;
```

#登录从库执行操作

```sql
CHANGE MASTER TO
     MASTER_HOST='172.18.0.3',
     MASTER_USER='reader',
     MASTER_PORT=3306,
     MASTER_PASSWORD='123456',
     MASTER_LOG_FILE='mysql-bin.000001',
     MASTER_LOG_POS=1165;
```

​	 
#查看从库状态

```sql
show slave status \G;
```

#重启从库复制相关线程

```sql
stop slave;
start slave;
```

#重置从库状态

```sql
reset slave;
```

#查看主库日志报错位置

```shell
mysqlbinlog --no-defaults -vv --base64-output=DECODE-ROWS /var/lib/mysql/mysql-bin.000118 |grep -A '452' 
```

#跳过当前事务

```sql
set global sql_slave_skip_counter=1;
```

#docker中创建桥接网络，以此来固定容器ip

docker network 创建  --driver 对应的模式   --subnet 内网网段   --gateway 网关  网络配置的名称

```shell
docker network create --driver bridge --subnet 172.18.0.0/16  --gateway 172.18.0.1 my-bridge
```

#将容器连接到自定义桥接网络

```shell
docker network connect my-bridge my-nginx
```

#将容器与用户自定义桥接网络断开连接

```shell
docker network disconnect my-bridge my-nginx
```

#删除桥接网络

```shell
docker network rm my-net
```

#已启动容器绑定指定ip和网桥

```shell
docker network connect --ip 172.18.0.3 my-bridge mysql-master
docker network connect --ip 172.18.0.4 my-bridge mysql-slave
```

#创建新容器连接到桥接网络，并指定固定IP

```shell
docker create --name my-nginx \
  --net mynetwork \
  --ip 172.18.0.2
  --publish 8080:80 \
  nginx:latest  
```

