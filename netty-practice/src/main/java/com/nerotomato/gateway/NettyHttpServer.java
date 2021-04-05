package com.nerotomato.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * netty实现服务端
 * Created by nero on 2021/4/1.
 */
@Slf4j
public class NettyHttpServer {
    public static void main(String[] args) {
        int port = 8808;
        NioEventLoopGroup mainLoopGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup subLoopGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //backlog 指定了内核为此套接口排队的最大连接个数。
            //对于给定的监听套接口，内核要维护两个队列: 未连接队列和已连接队列
            //SO_BACKLOG:Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    //TCP_NODELAY: TCP参数，立即发送数据，默认值为Ture
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //SO_KEEPALIVE: 连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //SO_REUSEADDR: 地址复用，默认值False
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    //SO_RCVBUF: TCP数据接收缓冲区大小
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    //SO_SNDBUF: TCP数据发送缓冲区大小
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    //SO_REUSEPORT:
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //ByteBuf的分配器
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            serverBootstrap.group(mainLoopGroup, subLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInitializer());

            Channel channel = serverBootstrap.bind(port).sync().channel();
            log.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }finally {
            //优雅退出，释放线程池资源
            mainLoopGroup.shutdownGracefully();
            subLoopGroup.shutdownGracefully();
        }
    }
}
