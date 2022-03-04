package com.malongbao.io.bio.thread_pool_demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Description:
 * date: 2022/2/28 17:46
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class ServerRunnableTarget implements Runnable {
    private Socket socket;

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {//这里server一直处理client的请求会一直占用请求
//            if ((msg = bufferedReader.readLine()) != null) {  这里是可以接收多个请求的，因为server接收完client的请求之后就关闭了，不会一直占用请求
                System.out.println("Server：" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerRunnableTarget(Socket socket) {
        this.socket = socket;

    }
}
