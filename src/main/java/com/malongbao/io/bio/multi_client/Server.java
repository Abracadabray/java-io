package com.malongbao.io.bio.multi_client;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description: 服务端接收多个客户端的请求，为每个client都起一个线程
 * date: 2022/2/28 11:38
 * 备注：线程的run()方法是由java虚拟机直接调用的，如果我们没有启动线程（没有调用线程的start()方法）而是在应用代码中直接调用run()方法，
 *      那么这个线程的run()方法其实运行在当前线程（即run()方法的调用方所在的线程）之中，而不是运行在其自身的线程中，从而违背了创建线程的初衷；
 *
 * @author Hy
 * @since JDK 1.8
 */
public class Server {
    public static void main(String[] args) {
        try {
            //1、定义一个ServerSocket对象进行服务端的端口注册
            ServerSocket ss = new ServerSocket(9999);

            //2、监听客户端的Socket连接请求
            while (true) {
                Socket socket = ss.accept();
                new ServerReaderThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
