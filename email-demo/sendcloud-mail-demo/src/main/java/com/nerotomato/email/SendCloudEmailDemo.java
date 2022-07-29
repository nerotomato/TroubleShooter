package com.nerotomato.email;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendCloudEmailDemo {

    // 普通邮件发送
    public static void send_common() throws IOException {
        // sendcloud接口地址 API URL
        final String url = "http://api.sendcloud.net/apiv2/mail/send";
        // 收件人 多个地址用;分隔
        final String to = "";
        // API_USER
        final String apiUser = "sendcloud 官网申请的apiUser";
        // API_KEY
        final String apiKey = "sendcloud 官网申请的apiKey";
        String subject = "主题";
        String html = "你好，这是通过代码发送的测试邮件!";
        // 发件人邮箱地址 后缀域名最好跟sendcloud官网配置的发信域名一致 防止显示代发
        String from = "";
        // 发件人名称 最好跟发件人邮箱前缀一致 防止显示代发
        String fromName = "";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("to", to));
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("fromName", fromName));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", html));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpClient.execute(httpPost);


        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        } else {
            System.err.println("error");
        }
        httpPost.releaseConnection();
    }
    public static void main(String[] args) throws IOException {
        send_common();
    }
}
