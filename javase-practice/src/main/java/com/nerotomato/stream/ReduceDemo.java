package com.nerotomato.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nerotomato
 * @date 2022/01/17
 * */
public class ReduceDemo {
    public static void main(String[] args) {
        final ArrayList<Integer> numList = Lists.newArrayList(1, 2, 3, 4);
        final Integer sum = numList.stream().reduce((integer, integer2) -> integer + integer2).get();
        System.out.println(sum);
    }
}
