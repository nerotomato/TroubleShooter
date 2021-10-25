package com.nerotomato.collection;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * ArrayList线程不安全
 * <p>
 * 如何使arraylist线程安全
 * - 1.ArrayList 的方法都加上 synchronized -> Vector
 * - 2.Collections.synchronizedList，强制将 List 的操作加上同步
 * - 3.Arrays.asList，不允许添加删除，但是可以 set 替换元素
 * - 4.Collections.unmodifiableList，不允许修改内容，包括添加删除和 set
 * Created by nero on 2021/4/13.
 */
public class SyncListDemo {
    public static void main(String[] args) {
        //List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> initList = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            initList.add(i);
        }
        //intList.add(10);
        //intList.set(8, 10);//可以修改内容，但不能变动元素数量
        //System.out.println(intList);
        final List<Object> list = new ArrayList<>();//正常list，可以操作
        list.addAll(initList);
        //System.out.println(list);

        List<Object> synchronizedList = Collections.synchronizedList(list);

        // 多线程操作
        // to do something
        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(new Supplier<Object>() {
            @Override
            public Object get() {
                return changeListElement(list);
            }
        });
        CompletableFuture<Object> completableFuture2 = CompletableFuture.supplyAsync(new Supplier<Object>() {
            @Override
            public Object get() {
                return reaChangedList(list);
            }
        });

        // 假如不再修改
        List list2 = Collections.unmodifiableList(list);

        System.out.println(list2.getClass());

        list2.set(8,10);

        System.out.println(Arrays.toString(list2.toArray()));

        list2.add(11);

        System.out.println(Arrays.toString(list2.toArray()));
        System.out.println(list);
        //System.out.println(synchronizedList);
        //System.out.println(Arrays.toString(synchronizedList.toArray()));
    }



    public static List changeListElement(List list) {

        for (int i = 0; i < list.size(); i++) {
            Integer num = (Integer) list.get(i);
            list.set(i, num * 10);
        }
        return list;
    }

    public static List reaChangedList(List list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("下标:" + i + "," + list.get(i));
        }
        return list;
    }
}
