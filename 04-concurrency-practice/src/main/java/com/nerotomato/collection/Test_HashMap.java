package com.nerotomato.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by nerotomato on 2021/10/28.
 */
public class Test_HashMap {
    public static void main(String[] args) throws Exception {

        Map<Object, Object> map = new HashMap<>(11);
        /**
         * put方法
         *
         * 由于和（length-1）运算，length 绝大多数情况小于2的16次方。所以始终是hashcode 的低16位（甚至更低）参与运算。要是高16位也参与运算，会让得到的下标更加散列。
         * 所以这样高16位是用不到的，如何让高16也参与运算呢。所以JDK1.8中hash(Object key)方法。让他的hashCode()和自己的高16位^运算。所以(h >>> 16)得到他的高16位与hashCode()进行^运算
         * */
        map.put("a", 1);
        System.out.println("1 >>> 3: " + (-1 >>> 3));
    }
}
