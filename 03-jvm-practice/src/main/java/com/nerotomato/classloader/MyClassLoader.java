package com.nerotomato.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * Created by nero on 2021/3/18.
 */
public class MyClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Object instance = new MyClassLoader().findClass("com.nerotomato.classloader.HelloClass").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //重写ClassLoader的findClass方法
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String base64Code = "yv66vgAAADQAIAoABwARCQASABMIABQKABUAFggAFwcAGAcAGQEABjxpbml0PgEAAygpVgEABENv" +
                "ZGUBAA9MaW5lTnVtYmVyVGFibGUBAARtYWluAQAWKFtMamF2YS9sYW5nL1N0cmluZzspVgEACDxj" +
                "bGluaXQ+AQAKU291cmNlRmlsZQEAD0hlbGxvQ2xhc3MuamF2YQwACAAJBwAaDAAbABwBABtUaGUg" +
                "bWFpbiBtZXRob2QgaXMgcnVubmluZyEHAB0MAB4AHwEAIEhlbGxvQ2xhc3MgaGFzIGJlZW4gaW5p" +
                "dGlhbGl6ZWQhAQAlY29tL25lcm90b21hdG8vY2xhc3Nsb2FkZXIvSGVsbG9DbGFzcwEAEGphdmEv" +
                "bGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJl" +
                "YW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7" +
                "KVYAIQAGAAcAAAAAAAMAAQAIAAkAAQAKAAAAHQABAAEAAAAFKrcAAbEAAAABAAsAAAAGAAEAAAAG" +
                "AAkADAANAAEACgAAACUAAgABAAAACbIAAhIDtgAEsQAAAAEACwAAAAoAAgAAAAwACAANAAgADgAJ" +
                "AAEACgAAACUAAgAAAAAACbIAAhIFtgAEsQAAAAEACwAAAAoAAgAAAAgACAAJAAEADwAAAAIAEA==";
        byte[] bytes = decodeBase64(base64Code);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] decodeBase64(String code) {
        return Base64.getDecoder().decode(code);
    }
}
