[TOC]

# 1.创建jenkins容器

**这里用的镜像是官方推荐的jenkins-blueocean镜像，集成了blueocean插件**

```shell
docker run \
  -u root \
  -d \
  -p 9090:8080 \
  -p 50000:50000 \
  -v /home/nerotomato/Develop/docker/jenkins-blueocean/jenkins_data:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --name jenkins-blueocean \
  jenkinsci/blueocean
```

