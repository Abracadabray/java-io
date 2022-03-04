package com.malongbao.io.netty.tcp_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description:
 * date: 2022/3/4 11:01
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class NettyServer {
    public static void main(String[] args) throws Exception {
        //bossGroup 只是处理连接请求,真正的和客户端业务处理，会交给 workerGroup完成
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)//设置两个线程组
                .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG, 128)//线程队列等待连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保证活动连接状态
//                .handler(null) // 该 handler对应 bossGroup , childHandler 对应 workerGroup
//                .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象(匿名对象)
                //给pipeline 设置处理器
//                    @Override
//                    protected void initChannel(SocketChannel ch) throws Exception {
//                        //可以使用一个集合管理 SocketChannel， 再推送消息时，可以将业务加入到各个channel 对应的 NIOEventLoop 的 taskQueue 或者 scheduleTaskQueue
//                        System.out.println("客户socketchannel hashcode=" + ch.hashCode());
//                        ch.pipeline().addLast(new NettyServerHandler());
//                    }
//                }); // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器
                .childHandler(new ChildHandlerChannelInitializer());
        System.out.println("============服务器准备完成============");
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("监听端口 6668 成功");
                } else {
                    System.out.println("监听端口 6668 失败");
                }
            }
        });
        channelFuture.channel().closeFuture().sync();
    }


    public static class ChildHandlerChannelInitializer extends ChannelInitializer {
        @Override
        protected void initChannel(Channel channel) throws Exception {
            //可以使用一个集合管理 SocketChannel， 再推送消息时，可以将业务加入到各个channel 对应的 NIOEventLoop 的 taskQueue 或者 scheduleTaskQueue
            System.out.println("客户schannel hashcode=" + channel.hashCode());
            channel.pipeline().addLast(new NettyServerHandler());
        }
    }
}
