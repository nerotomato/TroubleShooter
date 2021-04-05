package outbound.okhttp;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * OkHttp测试demo
 * Created by nero on 2021/3/28.
 */
public class OkHttpUtil {
    private static OkHttpClient okHttpClient = null;

    public static String execute(String url) {
        OkHttpClient okHttpClient = getOKHttpClient();
        final String[] message = new String[1];
        //String message=null;
        //代表Http请求的类是Request，该类使用构造器模式
        //最简单的构造get（默认是get请求体）
        Request request = new Request.Builder()
                .url(url).build();

        /**
         * 请求的发送有两种形式：
         * 一种是直接同步执行，阻塞调用线程，直接返回结果；（不推荐，Android中可能阻塞UI线程）
         * 另一种是通过队列异步执行，不阻塞调用线程，通过回调方法返回结果。如下所示：
         * */
        //Response response = null;
        //同步执行：如果返回null,代表超时或没有网络连接
        /*try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            long contentLength = response.body().contentLength();
             try {
                 message = response.body().string();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             System.out.println("响应内容长度为：" + contentLength);
            System.out.println("响应内容为：" + message);
        }*/

        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        //异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null) {
                    long contentLength = response.body().contentLength();
                    message[0] = response.body().string();
                    System.out.println("响应内容长度为：" + contentLength);
                    System.out.println("响应内容为：" + message[0]);
                }
            }
        });
        return message[0];
    }

    public static void main(String[] args) {
        OkHttpClient okHttpClient = getOKHttpClient();
        //代表Http请求的类是Request，该类使用构造器模式
        //最简单的构造get（默认是get请求体）

        Request request = new Request.Builder()
                .url("http://localhost:8801").build();

        /**
         * 请求的发送有两种形式：
         * 一种是直接同步执行，阻塞调用线程，直接返回结果；（不推荐，Android中可能阻塞UI线程）
         * 另一种是通过队列异步执行，不阻塞调用线程，通过回调方法返回结果。如下所示：
         * */
        //Response response = null;
        //同步执行：如果返回null,代表超时或没有网络连接
        //response = okHttpClient.newCall(request).execute();
        /* if (response != null) {
            long contentLength = response.body().contentLength();
            String message = response.body().string();
            System.out.println("响应内容长度为：" + contentLength);
            System.out.println("响应内容为：" + message);
        }*/

        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        //异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null) {
                    long contentLength = response.body().contentLength();
                    String message = response.body().string();
                    System.out.println("响应内容长度为：" + contentLength);
                    System.out.println("响应内容为：" + message);
                }
            }
        });
    }

    public static OkHttpClient getOKHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
