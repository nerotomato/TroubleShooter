1.创建zookeeper容器

```shell
docker run -d -p 2181:2181 --restart=always --name=zookeeper --privileged=true ^
-v D:\DockerDesktop\dockerContainer\zookeeper\data:/data ^
-v D:\DockerDesktop\dockerContainer\zookeeper\datalog:/datalog ^
-v D:\DockerDesktop\dockerContainer\zookeeper\logs:/logs ^
-e "ZOO_STANDALONE_ENABLED=true" ^
-e "TZ=Asia/Shanghai" ^
-e "ZOO_4LW_COMMANDS_WHITELIST=*" ^
-e "ZOO_AUTOPURGE_SNAPRETAINCOUNT=5" ^
-e "ZOO_AUTOPURGE_PURGEINTERVAL=24" ^
-e "ZOO_MAX_CLIENT_CNXNS=64" ^
-e "ZOO_TICK_TIME=2000" ^
-e "ZOO_INIT_LIMIT=100" ^
-e "ZOO_SYNC_LIMIT=5" ^
--net my-bridge ^
--ip 172.18.0.5 ^
zookeeper:latest
```



