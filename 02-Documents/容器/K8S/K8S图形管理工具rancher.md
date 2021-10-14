# 1.docker部署Rancher服务

```shell
docker run -p 80:80 -p 443:443 --name rancher \
--privileged \
--restart=unless-stopped \
-d rancher/rancher:v2.6-head
```

## 1.1.rancher中部署mysql的服务

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  # 指定Deployment的名称
  name: mysql-deployment
  # 指定Deployment的标签
  labels:
    app: mysql
  # 指定Deployment的空间
  namespace: default
spec:
  # 定义如何查找要管理的Pod
  selector:
  # 管理标签app为mysql的Pod
    matchLabels:
      app: mysql
      #workload.user.cattle.io/workloadselector: apps.deployment-default-undefined
   # 指定创建Pod的模板
  template:
    metadata:
      # 给Pod打上app:mysql标签
      labels:
      app: mysql
        #workload.user.cattle.io/workloadselector: apps.deployment-default-undefined
    # Pod的模板规约
    spec:
      containers:
        - imagePullPolicy: Always
          name: mysql
          # 指定容器镜像
          image: mysql:latest
          # 指定开放的端口
          ports:
            - containerPort: 3306
          # 设置环境变量
          env:
            - name: MYSQL_ROOT_PASSWORD
              value: 123456
          # 使用存储卷
          volumeMounts:
            # 将存储卷挂载到容器内部路径
            - mountPath: /var/log/mysql
              name: log-volume
            - mountPath: /var/lib/mysql
              name: data-volume
            - mountPath: /etc/mysql
              name: conf-volume
          _active: true
      affinity:
      imagePullSecrets:
      initContainers:
      restartPolicy: Always
      # 定义存储卷
      volumes:
        - name: log-volume
          # hostPath类型存储卷在宿主机上的路径
          hostPath:
            path: /home/docker/K8S/mysql/log
            # 当目录不存在时创建
            type: DirectoryOrCreate
        - name: data-volume
          hostPath:
            path: /home/docker/K8S/mysql/data
            type: DirectoryOrCreate
        - name: conf-volume
          hostPath:
            path: /home/docker/K8S/mysql/conf
            type: DirectoryOrCreate
  # 指定创建的Pod副本数量 
  replicas: 1
__clone: true
```

