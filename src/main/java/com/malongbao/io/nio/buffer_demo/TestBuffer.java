package com.malongbao.io.nio.buffer_demo;

import java.nio.ByteBuffer;

/**
 * Description:
 * date: 2022/3/2 9:56
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class TestBuffer {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1() {
        String str = "malongbao";
        //1. 分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("----------------allocate----------------");
        System.out.println("isDirect：" + byteBuffer.isDirect());
        System.out.println("position：" + byteBuffer.position());
        System.out.println("limit：" + byteBuffer.limit());
        System.out.println("mark：" + byteBuffer.mark());
        System.out.println("capacity：" + byteBuffer.capacity());

        //2. 利用 put() 存入数据到缓冲区中
        byteBuffer.put(str.getBytes());
        System.out.println("----------------put----------------");
        System.out.println("isDirect：" + byteBuffer.isDirect());
        System.out.println("position：" + byteBuffer.position());
        System.out.println("limit：" + byteBuffer.limit());
        System.out.println("mark：" + byteBuffer.mark());
        System.out.println("capacity：" + byteBuffer.capacity());

        //3. 切换读取数据模式
        byteBuffer.flip();
        System.out.println("----------------flip----------------");
        System.out.println("isDirect：" + byteBuffer.isDirect());
        System.out.println("position：" + byteBuffer.position());
        System.out.println("limit：" + byteBuffer.limit());
        System.out.println("mark：" + byteBuffer.mark());
        System.out.println("capacity：" + byteBuffer.capacity());

        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("----------------get----------------");
        System.out.println("get内容：" + new String(dst));
        System.out.println("isDirect：" + byteBuffer.isDirect());
        System.out.println("position：" + byteBuffer.position());
        System.out.println("limit：" + byteBuffer.limit());
        System.out.println("mark：" + byteBuffer.mark());
        System.out.println("capacity：" + byteBuffer.capacity());


        //5. rewind() : 可重复读
        byteBuffer.rewind();
        System.out.println("-----------------rewind()----------------");
        dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("isDirect：" + byteBuffer.isDirect());
        System.out.println("position：" + byteBuffer.position());
        System.out.println("limit：" + byteBuffer.limit());
        System.out.println("mark：" + byteBuffer.mark());
        System.out.println("capacity：" + byteBuffer.capacity());

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        byteBuffer.clear();
        System.out.println("-----------------clear()----------------");
        System.out.println("isDirect：" + byteBuffer.isDirect());
        System.out.println("position：" + byteBuffer.position());
        System.out.println("limit：" + byteBuffer.limit());
        System.out.println("mark：" + byteBuffer.mark());
        System.out.println("capacity：" + byteBuffer.capacity());
    }

    public static void test2() {
        String str = "malongbao";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2)); //ma
        System.out.println(buf.position());//2

        //mark() : 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));//lo
        System.out.println(buf.position());//4

        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println(buf.position());//2

        //判断缓冲区中是否还有剩余数据
        if (buf.hasRemaining()) {
            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining()); //longbao  ---> 7
        }
    }
}
