package com.nerotomato.stream;

import com.alibaba.fastjson.JSON;

import java.awt.print.Printable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by nero on 2021/4/22.
 */
public class StreamDemo1 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 2);
        print(list);
        Optional<Integer> first = list.stream().findFirst();
        print(first);
        System.out.println(first.map(i -> i * 100).orElse(100));

        int sum = list.stream().filter(i -> i < 5).distinct().reduce(0, (a, b) -> a + b);
        System.out.println("sum=" + sum);

        LinkedHashMap<Integer, Integer> linkedHashMap = list.stream()
                .parallel()
                .collect(Collectors.toMap(a -> a, a -> (a + 1), (a, b) -> a, LinkedHashMap::new));
        print(linkedHashMap);

        linkedHashMap.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));

        List<Integer> list1 = linkedHashMap.entrySet()
                .parallelStream()
                .map(e -> e.getKey() + e.getValue())
                .collect(Collectors.toList());
        print(list1);
    }

    private static void print(Object obj) {
        System.out.println(JSON.toJSONString(obj));
    }
}
