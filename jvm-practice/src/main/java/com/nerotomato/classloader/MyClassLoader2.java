package com.nerotomato.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义类加载器 2.0
 * Created by nerotomato on 2021/10/13.
 */
public class MyClassLoader2 extends ClassLoader {
    private String classPath;

    public MyClassLoader2(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = new byte[0];
        try {
            data = loadByte(name);
            //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节数组。
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    /**
     * 重写类加载方法，实现自己的加载逻辑，不委派给双亲加载
     */
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                // If still not found, then invoke findClass in order to find the class.
                long t1 = System.nanoTime();

                //非自定义的类还是走双亲委派加载
                if (!name.startsWith("com.nerotomato.classloader")) {
                    c = this.getParent().loadClass(name);
                } else {
                    c = findClass(name);
                }
                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    private byte[] loadByte(String name) throws IOException {
        name = name.replace(".", "/");
        FileInputStream fis = new FileInputStream(classPath + File.separator + name + ".class");
        int length = fis.available();
        byte[] data = new byte[length];
        fis.read(data);
        fis.close();
        return data;
    }

}
