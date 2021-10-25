package com.nerotomato.object;

/**
 * Created by nerotomato on 2021/10/15.
 */
// ‐XX:+UseCompressedOops 默认开启的压缩所有指针
// ‐XX:+UseCompressedClassPointers 默认开启的压缩对象头里的类型指针Klass Pointer
// Oops : Ordinary Object Pointers
// JVM中，对象大小默认为8的整数倍，若对象大小不能被8整除，则会填充空字节来填充对象保证。

class Person {
    int id; //4 byte
    String name; //4 byte 如果关闭压缩‐XX:‐UseCompressedOops，则占用8byte
    byte sex; //1 byte
    Object obj; //4 byte 如果关闭压缩‐XX:‐UseCompressedOops，则占用8byte
}
