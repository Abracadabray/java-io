package com.malongbao.io.bio.chat_demo;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Description:
 * date: 2022/3/1 0:03
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class ServerReaderThread extends Thread {

    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("消息广播......");
                sendMsg(msg);
                System.out.println("广播结束......");
            }
        } catch (Exception e) {
            System.out.println("有Client下线......");
            Server.socketList.remove(socket);
        }
    }

    //处理客户端发来的消息，推送给所有在线的socket
    private void sendMsg(String msg) throws Exception {
        List<Socket> sockets = Server.socketList;
        for (int i = 0; i < sockets.size(); i++) {
            if (this.socket == sockets.get(i)) {
                continue;
            }
            OutputStream outputStream = sockets.get(i).getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println(msg);
            printStream.flush();
            System.out.println(sockets.get(i) + ":" + msg);
        }
    }
}
