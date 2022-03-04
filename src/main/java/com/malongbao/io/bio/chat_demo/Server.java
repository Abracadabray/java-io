package com.malongbao.io.bio.chat_demo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:BIO模式下的端口转发思想-服务端实现
 * 服务端实现的需求：
 * 1、注册端口；
 * 2、接收客户端的socket连接，交给一个独立的线程来处理；
 * 3、把当前连接的客户端socket存入到一个在线socket集合中保存
 * 4、接收客户端的消息，然后推送给当前所有在线的socket接收
 * <p>
 * date: 2022/2/28 23:59
 *
 * @author Hy
 * @since JDK 1.8
 */
public class Server {
    public static List<Socket> socketList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            socketList.add(socket);
            new ServerReaderThread(socket).start();
        }
    }
}
