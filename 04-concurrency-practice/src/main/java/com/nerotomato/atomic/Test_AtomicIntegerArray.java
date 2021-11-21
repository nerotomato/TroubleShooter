package com.nerotomato.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray：原子更新整型数组里的元素
 * <p>
 * Created by nerotomato on 2021/10/25.
 */
public class Test_AtomicIntegerArray {
    private static int[] value = new int[]{1, 2, 3, 4, 5, 6};
    private static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        atomicIntegerArray.getAndSet(0, 8);
        System.out.println(atomicIntegerArray.get(0));
        System.out.println(value[0]);
    }
}
