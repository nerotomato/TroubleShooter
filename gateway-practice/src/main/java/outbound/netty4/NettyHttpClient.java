package outbound.netty4;

import filter.MyHttpRequestFilter;
import filter.MyHttpResponseFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import router.HttpEndpointRouter;
import router.RandomHttpEndpointRouter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * netty实现客户端
 * Created by nero on 2021/4/2.
 */
@Slf4j
public class NettyHttpClient {
    private List<String> backendUrls;
    private MyHttpResponseFilter responseFilter = new MyHttpResponseFilter();
    private HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public NettyHttpClient(List<String> backendsServers) {
        this.backendUrls = backendsServers;
        this.backendUrls = backendsServers.stream().map(this::formatUrl).collect(Collectors.toList());
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, MyHttpRequestFilter myHttpRequestFilter) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        //执行请求过滤器，添加请求头
        myHttpRequestFilter.filter(fullRequest, ctx);
        executeGet(fullRequest, ctx, url);
    }

    private void executeGet(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
        String host = "127.0.0.1";
        int port= 0;
        if (url != null) {
            if (url.contains("//")) {
               String temp = url.substring(url.indexOf("//") + 2);
                if(temp.contains(":")){
                    String[] split = temp.split(":");
                    host=split[0];
                    temp = split[1].substring(split[1].indexOf(":") + 1);
                    if(temp.contains("/")){
                        String[] tempSplit = temp.split("/");
                        port=Integer.parseInt(tempSplit[0]);
                    }
                }
            }
        }
        connect(fullRequest, host, port);
    }

    private static void connect(FullHttpRequest fullRequest, String host, int port) {
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
                    socketChannel.pipeline().addLast(new NettyHttpClientOutboundHandler(fullRequest));
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

    private String formatUrl(String backend) {
        return backend.endsWith("/") ? backend.substring(0, backend.length() - 1) : backend;
    }

}
