package com.malongbao.io.nio.nio_base_demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Description:
 * date: 2022/3/2 17:58
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class Client {
    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);//1. 获取通道
        socketChannel.configureBlocking(false);//2. 切换非阻塞模式
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); //3. 分配指定大小的缓冲区
        Scanner scanner = new Scanner(System.in);//4. 发送数据给服务端
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            byteBuffer.put(msg.getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        //5. 关闭通道
        socketChannel.close();
    }
}
