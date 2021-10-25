package com.nerotomato.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by nero on 2021/3/18.
 */
public class JvmClassLoaderAddUrl {
    public static void main(String[] args) {
        String classPath="file:/E:/IdeaProjects/TroubleShooter/jvm-practice/src/main/java/com/nerotomato/classloader";
        URLClassLoader urlClassLoader = (URLClassLoader) JvmClassLoaderAddUrl.class.getClassLoader();
        try {
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            URL url = new URL(classPath);
            addURL.invoke(urlClassLoader,url);
            Class.forName("com.nerotomato.classloader.HelloClass");

        } catch (NoSuchMethodException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
