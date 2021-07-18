# 1.JVM逃逸分析

**编写测试类**

```JAVA
package com.nerotomato.object;

import java.io.IOException;

public class EscapeAnalysis {

    public static Object globalObject;
    private static Object instance;

    public static void createGlobalObject() {
        globalObject = new Object();
    }

    public void createInstance() {
        instance = new Object();
    }

    public static int fn(int i) {
        User user = new User(i);
        return user.getAge();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int loop = 100000;
        int sum = 0;

        //
        //createGlobalObject();
        //new EscapeAnalysis().createInstance();

        for (int i = 0; i < loop; i++) {
            sum += fn(i);
        }
        System.out.println(sum);
        Thread.sleep(2000);
        for (int i = 0; i < loop; i++) {
            sum += fn(i);
        }
        System.out.println(sum);
        System.in.read();
    }
}

class User {
    int age;
    String name;

    public User(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
```

**通过java -cp . -Xmx3G -Xmn2G -server -XX:-DoEscapeAnalysis EscapeAnalysis运行代码，-XX:-DoEscapeAnalysis关闭逃逸分析，通过jps查看java进程的PID，接着通过jmap -histo [pid]查看java堆上的对象分布情况**

```SHELL
java -cp . -Xmx3G -Xmn2G -server -XX:-DoEscapeAnalysis EscapeAnalysis
```

**关闭逃逸分析后，可以看到200万User对象全部分配到了堆内存**

![image-20210708175803062](/home/nerotomato/.config/Typora/typora-user-images/image-20210708175803062.png)

**开启逃逸分析查看对象分布情况，jdk1.8默认支持逃逸分析**

```shell
java -cp . -Xmx3G -Xmn2G -server EscapeAnalysis
```

可以看到堆内存User对象明显减少

![image-20210708175453036](/home/nerotomato/.config/Typora/typora-user-images/image-20210708175453036.png)



**关闭分层编译**

```shell
java -cp . -Xmx3G -Xmn2G -server -XX:-TieredCompilation EscapeAnalysis
```

可以看到堆内存分配的User对象变得更少了

![image-20210708174853752](/home/nerotomato/.config/Typora/typora-user-images/image-20210708174853752.png)