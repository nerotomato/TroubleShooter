# 1.RockyLinux、Centos搭建docker环境

k8s集群依赖docker环境

docker版本: 20.10.10
k8s版本：1.20.10

```shell
1、关闭防火墙
systemctl stop firewalld
systemctl disable firewalld

2、关闭 selinux
sed -i 's/enforcing/disabled/' /etc/selinux/config # 永久关闭
setenforce 0 # 临时关闭

3.关闭 swap
 swapoff -a # 临时关闭
 vim /etc/fstab # 永久关闭
 #注释掉swap这行
 # /dev/mapper/centos-swap swap swap defaults 0 0

 systemctl reboot #重启生效
 free -m #查看下swap交换区是否都为0，如果都为0则swap关闭成功

4、给三台机器分别设置主机名
 hostnamectl set-hostname <hostname>
 第一台：k8s-master
 第二台：k8s-node1
 第三台：k8s-node2

5、在 k8s-master机器添加hosts，执行如下命令，ip需要修改成你自己机器的ip
     cat >> /etc/hosts << EOF
     192.168.65.160 k8s-master
     192.168.65.203 k8s-node1
     192.168.65.210 k8s-node2
     EOF

6、将桥接的IPv4流量传递到iptables
     cat > /etc/sysctl.d/k8s.conf << EOF
     net.bridge.bridge-nf-call-ip6tables = 1
     net.bridge.bridge-nf-call-iptables = 1
     EOF

7、设置时间同步
    yum install ntpdate -y
    ntpdate time.windows.com

8、添加k8s yum源
    cat > /etc/yum.repos.d/kubernetes.repo << EOF
    [kubernetes]
    name=Kubernetes
    baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
    enabled=1
    gpgcheck=0
    repo_gpgcheck=0
    gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
    EOF

9、如果之前安装过k8s，先卸载旧版本
yum remove -y kubelet kubeadm kubectl
 
10、查看可以安装的版本
sudo yum list kubelet --showduplicates | sort -r
 
11、安装kubelet、kubeadm、kubectl 指定版本，我们使用kubeadm方式安装k8s集群
sudo yum install -y kubelet-1.21.0-0 kubeadm-1.21.0-0 kubectl-1.21.0-0

12、开机启动kubelet
sudo systemctl enable kubelet
sudo systemctl start kubelet
```

# 2.在k8s-master机器上执行初始化操作

**初始化集群**

里面的第一个ip地址就是k8s-master机器的ip，改成自己机器的ip，后面两个ip网段不用动

```shell
sudo kubeadm init --apiserver-advertise-address=192.168.209.21 --image-repository registry.aliyuncs.com/google_containers --kubernetes-version v1.21.0 --service-cidr=10.2.0.0/16 --pod-network-cidr=10.3.0.0/16
```

**报错：**提示拉取 registry.aliyuncs.com/google_containers/coredns:v1.8.0 这个镜像失败

```shell
[ERROR ImagePull]: failed to pull image registry.aliyuncs.com/google_containers/coredns/coredns:v1.8.0: output: Error response from daemon: pull access denied for registry.aliyuncs.com/google_containers/coredns/coredns, repository does not exist or may require 'docker login': denied: requested access to the resource is denied
```

使用sudo docker images 查看已下载的镜像有哪些

发现已下载的镜像里面没有 registry.aliyuncs.com/google_containers/coredns/coredns:v1.8.0 这个镜像
使用 docker 命令手动拉取镜像

```shell
docker pull registry.aliyuncs.com/google_containers/coredns:1.8.0
```

kubernetes需要的是 registry.aliyuncs.com/google_containers/coredns/coredns:v1.8.0 这个镜像，使用 docker tag 命令重命名

```shell
# 重命名
sudo docker tag registry.aliyuncs.com/google_containers/coredns:1.8.0 registry.aliyuncs.com/google_containers/coredns/coredns:v1.8.0
# 删除原有镜像
sudo docker rmi registry.aliyuncs.com/google_containers/coredns:1.8.0
```

再次执行初始化

```shell
sudo kubeadm init --apiserver-advertise-address=192.168.209.21 --image-repository registry.aliyuncs.com/google_containers --kubernetes-version v1.21.0 --service-cidr=10.2.0.0/16 --pod-network-cidr=10.3.0.0/16
```



**在k8s-master机器上执行如下命令**

```shell
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config

#查看kubectl是否能正常使用
kubectl get nodes

#安装 Pod 网络插件
kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml
# 如果上面这个calico网络插件安装不成功可以试下下面这个
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kubeflannel.yml
```

# 3.将所有k8s node机器加入集群

**在k8s node机器执行，将node节点加入进master节点的集群里**

```shell
kubeadm join 192.168.209.21:6443 --token ceya38.egaf9ci42e22uvde \
--discovery-token-ca-cert-hash sha256:1fa67612c526b4a6ac410e3fcb3207c322a926534c7ed1809d0486f6dcf82d72
```

# 4.卸载k8s

```shell
yum remove -y kubelet kubeadm kubectl

kubeadm reset -f
modprobe -r ipip
lsmod
rm -rf ~/.kube/
rm -rf /etc/kubernetes/
rm -rf /etc/systemd/system/kubelet.service.d
rm -rf /etc/systemd/system/kubelet.service
rm -rf /usr/bin/kube*
rm -rf /etc/cni
rm -rf /opt/cni
rm -rf /var/lib/etcd
rm -rf /var/etcd
```

# 5.安装nginx

**用K8S部署Nginx**

```shell
# 创建一次deployment部署
kubectl create deployment nginx --image=nginx
kubectl expose deployment nginx --port=80 --type=NodePort
# 查看Nginx的pod和service信息
kubectl get pod,svc -o wide
```

