package com.nerotomato.object;

import org.openjdk.jol.info.ClassLayout;

/**
 * 计算对象大小
 * 使用-XX:-UseCompressedOops可以关闭指针压缩，包括对象指针 和 类型指针
 * 使用-XX:-UseCompressedClassPointers可以关闭类型指针
 * 使用-XX:+UseCompressedOops 可以开启指针压缩，包括对象指针 和 类型指针
 *
 * <p>
 * Created by nerotomato on 2021/10/15.
 */
public class JolTest {
    public static void main(String[] args) {
        ClassLayout objectLayout = ClassLayout.parseInstance(new Object());
        System.out.println(objectLayout.toPrintable());
        System.out.println("================================================");

        ClassLayout intArrayLayout = ClassLayout.parseInstance(new int[]{});
        System.out.println(intArrayLayout.toPrintable());
        System.out.println("================================================");

        ClassLayout personLayout = ClassLayout.parseInstance(new Person());
        System.out.println(personLayout.toPrintable());
    }

}
