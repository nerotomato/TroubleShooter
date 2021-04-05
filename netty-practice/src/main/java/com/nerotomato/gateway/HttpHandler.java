package com.nerotomato.gateway;

import com.nerotomato.httpclient.HttpClientUtil;
import com.nerotomato.netty.client.NettyHttpClient;
import com.nerotomato.okhttp.OkHttpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;

import java.io.UnsupportedEncodingException;

/**
 * Created by nero on 2021/4/1.
 */
@Slf4j
public class HttpHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取缓冲区中的客户端发来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            if (uri.contains("/test")) {
                handlerTest(fullRequest, ctx);
            }
           /* ByteBuf byteBuf = (ByteBuf) msg;
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            String message = new String(bytes, "UTF-8");
            log.info("The message from Client is :{}" + message);
            if ("hello,netty server.".equals(message)) {
                ByteBuf respBuf = Unpooled.copiedBuffer("hello,netty client!".getBytes("UTF-8"));
                ctx.write(respBuf);
            }*/
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 将消息发送队列中的消息，写入SocketChannel中发送给对方
     * netty的write方法并不直接将消息写入SocketChannel中，只是把待发送的消息放入发送缓冲数组
     * 调用flush方法会把发送缓冲数组的消息全部写到SocketChannel中
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 发生异常，关闭ChannelHandlerContext,释放和ChannelHandlerContext相关联的句柄等资源
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            //第一种：通过httpclient调用后台服务接口，获取响应数据
            //HttpGet httpGet = new HttpGet("http://127.0.0.1:8801");
            //String value = HttpClientUtil.execute(httpGet);
            //第二种：通过okhttp调用后台服务接口，获取响应数据
            String value2 = OkHttpUtil.execute("http://localhost:8801");
            //第三种：使用netty实现客户端访问后台访问接口，获取响应数据
            //String value3 = NettyHttpClient.execute("127.0.0.1", 8801);

            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(value2.getBytes("UTF-8")));

            response.headers().set("Content-type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (UnsupportedEncodingException e) {
            log.error("处理出错：" + e.getMessage());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);

                } else {
                    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }
    }
}
