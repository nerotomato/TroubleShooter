package com.nerotomato.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 自定义实现的Handler
 * 对网络事件进行读写操作
 * Created by nero on 2021/4/2.
 */
@Slf4j
public class MyNettyClientHandler extends ChannelInboundHandlerAdapter {

    //private final ByteBuf firstMessage;

    /*public MyNettyClientHandler(){
        byte[] reqbytes="hello,netty server.".getBytes();
        firstMessage= Unpooled.buffer(reqbytes.length);
        firstMessage.writeBytes(reqbytes);
    }*/
    //private FullHttpRequest request;

    /*public MyNettyClientHandler(String url) {
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, url);
    }*/

    /**
     * 当客户端端和服务端建立TCP连接后，会调用channlActive方法
     * channeActive方法调用channelHandlerContext的writeAndFlush方法发送数据给服务端
     */
    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }*/


    //服务端返回应答消息，调用channelRead方法，读取缓冲区中的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        //定义字节数组，通过readableBytes()方法获取缓冲区可读的字节数
        byte[] bytes = new byte[byteBuf.readableBytes()];
        //将缓冲区字节数组复制到新的数组bytes数组中
        byteBuf.readBytes(bytes);
        String body = new String(bytes, "UTF-8");
        log.info("The message from server is: {}" + body);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //出现异常，释放资源
        cause.printStackTrace();
        log.error("Unexpected exception:" + cause.getMessage());
        //释放客户端资源
        ctx.close();
    }
}
