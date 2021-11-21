package com.nerotomato.atomic;

import java.util.*;

/**
 * Created by nerotomato on 2021/10/26.
 */
public class TestScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] s = line.split(" ");
            List<String> strings = Arrays.asList(s);
            Collections.sort(strings);
            for (int i = 0; i < strings.size(); i++) {
                if (i == strings.size() - 1) {
                    System.out.print(strings.get(i));
                }else{
                    System.out.print(strings.get(i) + " ");
                }
            }
        }
    }

}
