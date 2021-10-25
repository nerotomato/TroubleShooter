package com.nerotomato.object;

/**
 * JVM对象逃逸分析和栈上分配
 * 标量替换
 * <p>
 * Created by nerotomato on 2021/10/15.
 */
public class AllocateOnStack {
    /**
     * 关闭逃逸分析，开启标量替换，会发生大量GC
     * -Xmx15m -Xms15m -XX:-DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations
     * 开启逃逸分析，关闭标量替换，会发生大量GC
     * -Xmx15m -Xms15m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-EliminateAllocations
     * 开启逃逸分析，开启标量替换，不会发生GC,对象在栈上分配
     * -Xmx15m -Xms15m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations
     *
     * 结论：
     * 对象栈上分配，需要同时开启逃逸分析和标量替换
     *
     * JVM通过逃逸分析确定该对象不会被外部访问。如果不会逃逸可以将该对象在栈上分配内存，这样该对象所占用的
     * 内存空间就可以随栈帧出栈而销毁，就减轻了垃圾回收的压力
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            allocate();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time cost is:" + (end - start) + " ms");
    }

    private static void allocate() {
        User user = new User();
        user.setAge(26);
        user.setName("nero");
    }
}
