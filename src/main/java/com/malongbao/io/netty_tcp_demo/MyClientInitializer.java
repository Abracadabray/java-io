package com.malongbao.io.netty_tcp_demo;

/**
 * Description:
 * date: 2022/3/17 15:36
 *
 * @author Hy
 * @since JDK 1.8
 */
import com.malongbao.io.netty_chain_demo.MyClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyClientHandler());
    }
}

