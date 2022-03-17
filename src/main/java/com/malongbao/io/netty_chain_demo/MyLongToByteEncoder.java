package com.malongbao.io.netty_chain_demo;

/**
 * Description:
 * date: 2022/3/17 11:02
 *
 * @author Hy
 * @since JDK 1.8
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    //编码方法
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg=" + msg);
        out.writeLong(msg);

    }
}

