package com.malongbao.io.bio.multi_client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Description:
 * date: 2022/2/28 16:00
 *
 * @author Hy
 * @since JDK 1.8
 */
public class ServerReaderThread extends Thread {
    public Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                System.out.println("Server收到消息:" + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
