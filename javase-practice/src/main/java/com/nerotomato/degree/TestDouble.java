package com.nerotomato.degree;

import java.math.BigDecimal;

/**
 * 测试精度问题
 * Created by nerotomato on 2021/10/12.
 */
public class TestDouble {
    public static void main(String[] args) {
        double d1 = 0.1d;
        double d2 = 0.3d;
        //double parseDouble = Double.valueOf("0.225");
        BigDecimal bigDecimal1 = new BigDecimal("0.1");
        BigDecimal bigDecimal2 = new BigDecimal("0.3");
        BigDecimal add = bigDecimal1.add(bigDecimal2);
        BigDecimal result = bigDecimal2.subtract(bigDecimal1);
        System.out.println(result);
        System.out.println(d2 - d1);
        String a = "87654321";
        String b = "95748924";

        StringBuffer sb = new StringBuffer();



    }
}
