package com.nerotomato.bytecode;

import com.google.common.net.MediaType;
import sun.misc.BASE64Encoder;

import java.io.*;
/**
 * Base64工具类
 * @author nerotomato
 * @date 20220811
 * */
public class Base64Utils {

    /**
     * 根据字节数组和文件类型拼接base64字符串
     * */
    public static String getBase64(byte[] bytes, String contentType){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return "data:" + contentType + ";base64," + base64Encoder.encode(bytes);
    }

    public static void main(String[] args) throws IOException {
        final File file = new File("/Users/nerotomato/Downloads/0f842666e70ae47bba831e696544a074.jpeg");
        final long length = file.length();
        final ByteArrayOutputStream bos = new ByteArrayOutputStream((int) length);
        final BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = br.read(bytes,0,1024))!= -1){
            bos.write(bytes,0,len);
        }
        final byte[] byteArray = bos.toByteArray();
        final String contentType = MediaType.TIFF.toString();
        System.out.println(getBase64(byteArray,contentType));
    }
}