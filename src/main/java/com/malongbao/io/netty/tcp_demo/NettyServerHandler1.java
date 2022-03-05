package com.malongbao.io.netty.tcp_demo;

/**
 * Description:
 * date: 2022/3/4 16:45
 *
 * @author Hy
 * @since JDK 1.8
 */

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;
/**
 * 说明
 * 1. 我们自定义一个Handler 需要继续netty 规定好的某个HandlerAdapter(规范)
 * 2. 这时我们自定义一个Handler , 才能称为一个handler
 */
public class NettyServerHandler1 extends ChannelInboundHandlerAdapter {
    //读取数据实际(这里我们可以读取客户端发送的消息)

    /**
     * 1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
     * 2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        // 比如这里我们有一个非常耗时长的业务-> 异步执行 -> 提交该channel 对应的
        // NIOEventLoop 的 taskQueue中,

        /**
         *  解决方案1 用户程序自定义的普通任务
         */
        channelHandlerContext.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端--------------2", CharsetUtil.UTF_8));
                    System.out.println("channel code=" + channelHandlerContext.channel().hashCode());
                } catch (Exception ex) {
                    System.out.println("发生异常" + ex.getMessage());
                }
            }
        });

        channelHandlerContext.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5 * 1000);
                channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端--------------3", CharsetUtil.UTF_8));
                System.out.println("channel code=" + channelHandlerContext.channel().hashCode());
            } catch (Exception ex) {
                System.out.println("发生异常" + ex.getMessage());
            }
        });

        /**
         * 解决方案2 : 用户自定义定时任务 -> 该任务是提交到 scheduleTaskQueue中
         */
        channelHandlerContext.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(5 * 1000);
                channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端--------------4", CharsetUtil.UTF_8));
                System.out.println("channel code=" + channelHandlerContext.channel().hashCode());
            } catch (Exception ex) {
                System.out.println("发生异常" + ex.getMessage());
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("go on ...");
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是 write + flush
        //将数据写入到缓存，并刷新
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端--------------1", CharsetUtil.UTF_8));
        //TODO:由于channelRead是新起线程池运行，线程运行的顺序就是位置的，加上有sleep操作，所以这条语句会先输出
    }

    //处理异常, 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
