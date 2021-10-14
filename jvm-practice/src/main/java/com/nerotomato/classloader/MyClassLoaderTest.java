package com.nerotomato.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 模拟Tomcat打破双亲委派机制
 * Created by nerotomato on 2021/10/13.
 */
public class MyClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
        MyClassLoader2 myClassLoader = new MyClassLoader2("/home/nerotomato/Develop/java_develop/test/testjvm/");
        // 执行loadClass，由于默认的双亲委派机制，会使用AppClassLoader加载类
        //打破双亲委派机制，自己的类使用自定义类加载器加载，其他的非自定义的类还使用默认的双亲委派机制，
        Class<?> clazz1 = myClassLoader.loadClass("com.nerotomato.classloader.HelloClass");
        Object o = clazz1.newInstance();
        Method main = clazz1.getDeclaredMethod("main", String[].class);
        //反射调用main方法，执行invoke时要将参数强转为Object
        main.invoke(o, (Object) new String[]{});
        //上面代码会被编译成：m.invoke(o,"a","b"),这样就发生参数个数不匹配的问题
        //因此，必须加上(Object)进行转型，避免这个问题
        System.out.println(clazz1.getClassLoader().getClass().getName());
        System.out.println("===========================================");

        MyClassLoader2 myClassLoader2 = new MyClassLoader2("/home/nerotomato/Develop/java_develop/test/testjvm2/");
        // 执行loadClass，由于默认的双亲委派机制，会使用AppClassLoader加载类
        //打破双亲委派机制，自己的类使用自定义类加载器加载，其他的非自定义的类还使用默认的双亲委派机制，
        Class<?> clazz2 = myClassLoader2.loadClass("com.nerotomato.classloader.HelloClass");
        Object o2 = clazz2.newInstance();
        Method main2 = clazz2.getDeclaredMethod("main", String[].class);
        main2.invoke(o2, (Object) new String[]{});
        System.out.println(clazz2.getClassLoader().getClass().getName());
    }
}
