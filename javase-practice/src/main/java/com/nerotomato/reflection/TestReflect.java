package com.nerotomato.reflection;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射-测试
 * Created by nero on 2021/2/26.
 */
@Slf4j
public class TestReflect {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        //第一种方式：Object.class
        //Class<TestObject> objectClass = TestObject.class;
        //第二种方式：Class.forName()传入类的路径获取
        //Class<TestObject> objectClass = (Class<TestObject>) Class.forName("com.nerotomato.reflection.TestObject");
        //第三种方式：通过对象实例instance.getClass()获取
        //TestObject to = new TestObject();
        //Class<? extends TestObject> objectClass = to.getClass();
        //第四种方式：通过类加载器xxxClassLoader.loadClass()传入类路径获取

        Class<TestObject> objectClass = (Class<TestObject>) ClassLoader.getSystemClassLoader().loadClass("com.nerotomato.reflection.TestObject");


        /**
         * 获取TestObject类的Class对象并且创建TestObject类实例
         */
        //通过类加载器获取Class对象不会进行初始化，意味着不进行包括初始化等一些列步骤，静态块和静态对象不会得到执行
        //但是通过class对象创建实例后，会进行初始化，下面还是获取到了静态代码块中执行的结果
        TestObject to = objectClass.newInstance();

        /**
         * 获取类中所有定义的方法
         */
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method m : declaredMethods) {
            //log.info("Method name:{}", m.getName());
            if ("getSex".equals(m.getName())) {
                log.info("" + m.invoke(to));
            }
            if ("getAge".equals(m.getName())) {
                log.info("" + m.invoke(to));
            }
        }
        //必须指定参数类型，反正会报错:NoSuchMethodException
        Method tmup = objectClass.getDeclaredMethod("translateNameToUpCase", String.class);
        //为了调用private方法,取消安全检查
        tmup.setAccessible(true);
        tmup.invoke(to, "nerotomato");
        //必须指定参数类型，反正会报错:NoSuchMethodException
        Method md5pwd = objectClass.getDeclaredMethod("md5PWD", String.class);
        //为了调用private方法,取消安全检查
        md5pwd.setAccessible(true);
        md5pwd.invoke(to, "123456");

        Method getName = objectClass.getDeclaredMethod("getUserName");
        log.info((String) getName.invoke(to));
        Method getPassword = objectClass.getDeclaredMethod("getPassword");
        log.info((String) getPassword.invoke(to));
    }
}
