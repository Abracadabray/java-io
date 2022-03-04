package com.malongbao.io.bio.chat_demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Description:
 * date: 2022/3/1 0:00
 *
 * @author Hy
 * @since JDK 1.8
 */
public class ClientReaderThread extends Thread {
    private Socket socket;

    public ClientReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("收到消息：" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
