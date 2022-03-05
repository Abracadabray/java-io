package com.malongbao.io.netty.tcp_demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Description:
 * date: 2022/3/4 11:09
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class NettyClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();//客户端需要一个事件循环组
        Bootstrap bootstrap = new Bootstrap(); //创建客户端启动对象
        bootstrap.group(workerGroup)//设置线程组
                .handler(new MyChannelInitializer())//加入自己的处理器
                .channel(NioSocketChannel.class);// 设置客户端通道的实现类(反射)
        System.out.println("客户端 ok..");
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",9999).sync();  //关于 ChannelFuture 要分析，涉及到netty的异步模型
        channelFuture.channel().closeFuture().sync(); //对关闭通道事件  进行监听
    }

    public static class MyChannelInitializer extends ChannelInitializer {
        @Override
        protected void initChannel(Channel channel) throws Exception {
            channel.pipeline().addLast(new NettyClientHandler()); //加入自己的处理器
        };
    }
}
