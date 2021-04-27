package com.nerotomato.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by nero on 2021/4/24.
 */
public class GuavaDemo {
    private static EventBus bus = new EventBus();

    static {
        bus.register(new GuavaDemo());
    }

    public static void main(String[] args) {
        testEventBus();
        List<String> stringList = testString();
        List<Integer> integers = testList();
        print("integers:" + integers);
        testMap(integers);
        testBiMap(stringList);
    }

    private static void testEventBus() {
        // EventBus
        // SPI+service loader
        // Callback/Listener
        Student student = new Student(1, 25, "nero");
        System.out.println(Thread.currentThread().getName() + ":I want student:" + student.getName() + " to run now!");
        bus.post(new AEvent(student));
    }

    @Data
    @AllArgsConstructor
    public static class AEvent {
        private Student student;
    }

    @Subscribe
    public void handle(AEvent ae) {
        System.out.println(Thread.currentThread().getName() + ":" + ae.student.getName() + " is running!");
    }

    private static List<String> testString() {
        // 字符串处理
        List<String> list = Lists.newArrayList("a", "b", "g", "8", "9");
        //集合转字符串
        String joinResult = Joiner.on(",").join(list);
        System.out.println(joinResult);

        String test = "34344,,,34,34,哈哈";
        list = Splitter.on(",").splitToList(test);
        System.out.println(list);
        return list;
    }

    private static List<Integer> testList() {
        // 更强的集合操作
        // 简化 创建
        List<Integer> integers = Lists.newArrayList(4, 2, 3, 5, 1, 2, 2, 7, 6);
        List<List<Integer>> partition = Lists.partition(integers, 3);
        print(partition);
        return integers;
    }

    private static void testMap(List<Integer> list) {
        //Map map = list.stream().collect(Collectors.toMap(a -> a, a -> (a + 1)));
        Multimap<Integer, Integer> bMultimap = ArrayListMultimap.create();
        list.forEach(
                a -> bMultimap.put(a, a + 1)
        );
        print(bMultimap);
    }
    private static void testBiMap(List<String> lists) {
        BiMap<String, Integer> words = HashBiMap.create();
        words.put("First", 1);
        words.put("Second", 2);
        words.put("Third", 3);

        System.out.println(words.get("Second").intValue());
        System.out.println(words.inverse().get(3));

        Map<String,String> map1 = Maps.toMap(lists.listIterator(), a -> a+"-value");
        print(map1);
    }
    private static void print(Object obj) {
        System.out.println(JSON.toJSONString(obj));
    }
}
