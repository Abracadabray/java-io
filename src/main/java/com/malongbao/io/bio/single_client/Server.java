package com.malongbao.io.bio.single_client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description: 服务端只可以接收一个客户端的请求,多个client发送消息也只能接收到其中的一个client
 * date: 2022/2/28 11:38
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
            Socket socket = ss.accept();

            //3、从socket管道中得到一个字节输入流对象
            InputStream inputStream = socket.getInputStream();

            //4、把字节输入流包装成缓冲字节输入流
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
