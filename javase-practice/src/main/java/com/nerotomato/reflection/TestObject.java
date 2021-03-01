package com.nerotomato.reflection;

import lombok.Data;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 通过定义静态属性，静态代码块，方法对获取class对象的四种方式进行测试
 * Created by nero on 2021/2/26.
 */
@Data
public class TestObject {
    private String name;
    private String password;
    //静态属性
    private static String sex = "male";
    private static int age;

    //静态代码块
    static {
        //sex = "female";
        //age = 25;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getUserName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    private void translateNameToUpCase(String name) {
        this.name = name.toUpperCase();
    }

    private void md5PWD(String password) {
        //确定加密算法
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Base64.Encoder base64Encoder = Base64.getEncoder();
        //加密字符串
        this.password = base64Encoder.encodeToString(md5.digest(password.getBytes()));
    }

}
