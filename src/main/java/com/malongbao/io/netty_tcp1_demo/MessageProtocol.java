package com.malongbao.io.netty_tcp1_demo;

/**
 * Description:
 * date: 2022/3/17 16:01
 *
 * @author Hy
 * @since JDK 1.8
 */
//协议包
public class MessageProtocol {
    private int len; //关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
