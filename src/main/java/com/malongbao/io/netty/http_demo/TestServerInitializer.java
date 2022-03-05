package com.malongbao.io.netty.http_demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Description:
 * date: 2022/3/5 12:59
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //得到管道
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        //加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
        channelPipeline.addLast("MyHttpServerCodec", new HttpServerCodec());//HttpServerCodec 是netty 提供的处理http的 编-解码器
        //2. 增加一个自定义的handler
        channelPipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

        System.out.println("ok.............");
    }
}
