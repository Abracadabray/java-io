package com.malongbao.io.bio.file_transfer_demo;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

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
            //1、得到一个数据输入流读取客户端发送过来的数据
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //2、读取客户端发送的文件类型
            String suffix = dataInputStream.readUTF();
            System.out.println("服务端接收文件类型：" + suffix);
            //3、定义一个字节输入管道
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\ProjectSpaces\\java-io\\src\\main\\resources\\file_out\\" + UUID.randomUUID() + suffix);
            //4、从数据输入流中读取文件数据，写出到字节输出流中
            byte[] buffer = new byte[1024];
            int len;
            while ((len = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.close();
            System.out.println("服务端接收文件并保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
