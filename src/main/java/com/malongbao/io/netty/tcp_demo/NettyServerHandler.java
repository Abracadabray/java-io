package com.malongbao.io.netty.tcp_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Description:
 * date: 2022/3/4 11:08
 *
 * @author Hy
 * @since JDK 1.8
 */

/**
 * 说明
 * 1. 我们自定义一个Handler 需要继承netty 规定好的某个HandlerAdapter(规范)
 * 2. 这时我们自定义一个Handler , 才能称为一个handler
 */
@SuppressWarnings("all")
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据事件(这里我们可以读取客户端发送的消息)
    /**
     * 1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
     * 2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        System.out.println("客户端地址:" + channel.remoteAddress());
        ByteBuf byteBuf = (ByteBuf) msg;// ByteBuf 是 Netty 提供的，不是 NIO 的 ByteBuffer.
        System.out.println("客户端发送消息是:" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        // writeAndFlush 是 write + flush
        // 将数据写入到缓存，并刷新
        // 一般讲，我们对这个发送的数据进行编码
        System.out.println("channelReadComplete......");
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端--------------", CharsetUtil.UTF_8));
    }

    //发生异常后, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
    }


}
