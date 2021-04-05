package com.nerotomato.gateway;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by nero on 2021/4/1.
 */
public class HttpInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new HttpServerCodec());
        /**
         * 当我们用POST方式请求服务器的时候，对应的参数信息是保存在message body中的,如果只是单纯的用HttpServerCodec是无法完全的解析Http POST请求的，因为HttpServerCodec只能获取uri中参数，所以需要加上HttpObjectAggregator
         * HttpObjectAggregator 是 Netty 提供的 HTTP 消息聚合器，通过它可以把 HttpMessage 和 HttpContent 聚合成一个 FullHttpRequest 或者 FullHttpResponse(取决于是处理请求还是响应），方便我们使用。
         * 另外，消息体比较大的话，可能还会分成好几个消息体来处理，HttpObjectAggregator 可以将这些消息聚合成一个完整的，方便我们处理。
         * 使用方法：将 HttpObjectAggregator 添加到 ChannelPipeline 中，如果是用于处理 HTTP Request 就将其放在 HttpResponseEncoder 之后，反之，如果用于处理 HTTP Response 就将其放在 HttpResponseDecoder 之后。
         * */
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new HttpHandler());
    }
}
