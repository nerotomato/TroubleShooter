package com.nerotomato.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * lamda collection
 * Created by nero on 2021/4/22.
 */
public class CollectionDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2, 5, 7, 9, 1, 4, 3, 6, 10, 8);
        print(list);
        Collections.sort(list);
        print(list);
        Collections.shuffle(list);
        print(list);
        Collections.reverse(list);
        print(list);
        System.out.println(Collections.frequency(list, 2));
        System.out.println(Collections.max(list));
        Collections.fill(list, 20);
        print(list);
        list = Collections.singletonList(8);
        print(list);

    }

    private static void print(List<Integer> list) {
        System.out.println(String.join(",", list.stream().map(i -> i.toString())
                .collect(Collectors.toList())
                .toArray(new String[]{})));
    }
}
