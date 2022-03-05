package com.malongbao.io.netty.http_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * Description:
 * date: 2022/3/5 13:07
 *
 * @author Hy
 * @since JDK 1.8
 */

/*
说明
    1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
    2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 */
@SuppressWarnings("all")
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        System.out.println("对应的channel = " + channelHandlerContext.channel());
        System.out.println("对应的pipeline = " + channelHandlerContext.pipeline());
        System.out.println("channel().pipeline = " + channelHandlerContext.channel().pipeline());
        System.out.println("channelHandlerContext.handler = " + channelHandlerContext.handler());
        System.out.println("channelHandlerContext.getClass = " + channelHandlerContext.getClass());
        System.out.println("pipeline().hashCode = " + channelHandlerContext.pipeline().hashCode());
        System.out.println("this.hashCode = " + this.hashCode());
        System.out.println("httpObject.getClass = " + httpObject.getClass());
        System.out.println("channel().remoteAddress() = " + channelHandlerContext.channel().remoteAddress());

        if (httpObject instanceof HttpRequest) {
            //获取到HttpRequest
            HttpRequest httpRequest = (HttpRequest) httpObject;
            URI uri = new URI(httpRequest.uri());//获取uri
            if ("/test".equals(uri.getPath())) {
                System.out.println("请求了/test, 不做响应");
                return;
            }

            //给浏览器进行消息回复
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,i am server", CharsetUtil.UTF_8);//进行消息回复
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf); //构造一个http的相应，即 httpresponse
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(response);//将构建好 response返回
        }
    }
}
