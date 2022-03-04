package com.malongbao.io.nio.channel_demo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description:见 readme.md
 * <p>
 * date: 2022/3/2 11:22
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class TestChannel {
    public static void main(String[] args) throws Exception {
//        write();
//        read();
//        copyFile();
//        ScatterAndGather();
//        transferFrom();
        transferTo();
    }

    public static void write() throws Exception {
        //1、定义一个文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream("data.txt");

        //2、得到文件输出流对应的通道channel
        FileChannel fileChannel = fileOutputStream.getChannel();

        //3、分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("malongbao-qiuqiu".getBytes());
        //4、把缓冲区切换到写出模式
        byteBuffer.flip();

        //5、向文件输出流中写数据
        fileChannel.write(byteBuffer);
        fileChannel.close();
    }


    public static void read() throws Exception {
        //1、定义一个输入流
        FileInputStream fileInputStream = new FileInputStream("data.txt");

        //2、定义一个文件Channel
        FileChannel fileChannel = fileInputStream.getChannel();

        //3、定义一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);
        //4、缓冲区可写
        byteBuffer.flip();

        //5、读取出缓冲区中的数据并输出
        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
    }


    public static void copyFile() throws Exception {
        //定义一个输入流
        File srcFile = new File("data.txt");
        FileInputStream fileInputStream = new FileInputStream(srcFile);
        FileChannel srcFileChannel = fileInputStream.getChannel();

        //定义一个输出流
        File descFile = new File("data_copy.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(descFile);
        FileChannel descFileChannel = fileOutputStream.getChannel();

        //定义一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {  //有可能文件超出1024，这个时候需要进行下一次文件的读取
            buffer.clear();

            //读数据
            int flag = srcFileChannel.read(buffer);
            if (flag == -1) {
                System.out.println("写文件结束......");
                break;
            }

            //切换写模式
            buffer.flip();

            //写数据
            descFileChannel.write(buffer);
        }


        //关闭资源
        srcFileChannel.close();
        descFileChannel.close();
    }


    public static void ScatterAndGather() throws Exception {
        RandomAccessFile readRandomAccessFile = new RandomAccessFile("1.txt", "rw");
        //1. 获取通道
        FileChannel channel1 = readRandomAccessFile.getChannel();

        //2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufs = {buf1, buf2};


        //3. 分散读取:前一个 buffer 被填满数据时才会填后一个 buffer；
        channel1.read(bufs);
        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

//        System.out.println(bufs[0].mark());
//        System.out.println(bufs[1].mark());

        System.out.println("bufs[0]:" + new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("bufs[1]:" + new String(bufs[1].array(), 0, bufs[1].limit()));


        //4. 聚集写入:将 bufs 数组中各 buffer 所含数据写入 channel；
        //该方法只会将 buffer 中的有效数据写入 channel；
        //即，示例中的 header 容量是 128 个字节（byte）；如果其有效数据只有 50 个字节，则此50个字节被写入 channel 后，就会开始将 body 中的数据写入 channel。
        //相对 Scatter 的“固定长度”而言，Gather 的处理是动态的“可变长度”。所以在使用要特别注意约定好数据格式，防止“约定长度”不一致导致接收方无法正确解析数据。
        RandomAccessFile writeRandomAccessFile = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = writeRandomAccessFile.getChannel();
        channel2.write(bufs);
    }


    public static void transferFrom() throws Exception {
        // 1、字节输入管道
        FileInputStream is = new FileInputStream("data.txt");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("data_transfer.txt");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        osChannel.transferFrom(isChannel, isChannel.position(), isChannel.size());
        isChannel.close();
        osChannel.close();
    }


    public static void transferTo() throws Exception {
        // 1、字节输入管道
        FileInputStream is = new FileInputStream("data.txt");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("data_transferTo.txt");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        isChannel.transferTo(isChannel.position(), isChannel.size(), osChannel);
        isChannel.close();
        osChannel.close();
    }


}
