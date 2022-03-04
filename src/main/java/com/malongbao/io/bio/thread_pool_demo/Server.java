package com.malongbao.io.bio.thread_pool_demo;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description: 开发伪异步
 * date: 2022/2/28 17:19
 *
 * @author Hy
 * @since JDK 1.8
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);

        //最大3个线程，最大队列是10
        HandlerSocketServerPool handlerSocketServerPool = new HandlerSocketServerPool(3, 10);

        while (true) {
            Socket socket = serverSocket.accept();
            Runnable target = new ServerRunnableTarget(socket);
            handlerSocketServerPool.execute(target);
        }
    }
}
