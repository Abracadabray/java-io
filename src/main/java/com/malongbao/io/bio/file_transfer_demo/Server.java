package com.malongbao.io.bio.file_transfer_demo;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:服务端开发，可以实现接收客户端的任意类型文件，并保存到服务端磁盘
 * date: 2022/2/28 21:41
 *
 * @author Hy
 * @since JDK 1.8
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            new ServerReaderThread(socket).start();
        }
    }

}
