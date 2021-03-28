package com.nerotomato.encoding;

import java.util.Arrays;
import java.util.Base64;

/**
 * Created by nero on 2021/3/22.
 */
public class TestBase64 {
    public static void main(String[] args) {
        String password = "12345678￥a";
        System.out.println(password.getBytes().toString());
        String base64Code = encode(password);
        byte[] decodes = decode(base64Code);
        System.out.println(Arrays.toString(decodes));
    }

    private static byte[] decode(String encode) {
        return Base64.getDecoder().decode(encode);
    }

    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
