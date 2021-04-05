package com.nerotomato.netty.client;

import com.nerotomato.netty.handler.MyNettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * netty实现客户端
 * Created by nero on 2021/4/2.
 */
@Slf4j
public class NettyHttpClient {
    public static void main(String[] args) {
        int port = 8808;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }
        connect("127.0.0.1", port);
    }

    private static void connect(String host, int port) {
        //配置客户端NIO线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //客户端辅助启动类
            Bootstrap bs = new Bootstrap();
            bs.group(workerGroup);
            bs.channel(NioSocketChannel.class);
            bs.option(ChannelOption.SO_KEEPALIVE, true);
            bs.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //将它的ChannelHandler:MyNettyClientHandler设置到ChannelPipeline,用于处理网络IO事件
                    socketChannel.pipeline().addLast(new MyNettyClientHandler());
                }
            });
            // 发起异步连接操作
            ChannelFuture channelFuture = bs.connect(host, port).sync();
            //等待客户端链路关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            //优雅退出，释放NIO线程组
            workerGroup.shutdownGracefully();
        }
    }

    public static String execute(String host, int port) {
        connect("127.0.0.1", port);
        return null;
    }
}
