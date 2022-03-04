package com.malongbao.io.bio.file_transfer_demo;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * Description:实现客户端上传任意类型的文件数据给服务端保存起来
 * date: 2022/2/28 21:29
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class TxtClient {
    public static void main(String[] args) throws Exception {
        //1、请求与服务器的socket链接
        Socket socket = new Socket("127.0.0.1", 9999);
        //2、把字节输出流包装成一个数据输出流
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        //3、先发送上传文件的后缀给服务器
        dataOutputStream.writeUTF(".txt");
        //4、把文件数据发送给服务端进行接收
        FileInputStream fileInputStream = new FileInputStream("D:\\ProjectSpaces\\java-io\\src\\main\\resources\\file_in\\text.txt");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            dataOutputStream.write(buffer, 0, len);
        }
        dataOutputStream.flush();
        socket.shutdownOutput();//socket关闭输出流,通知服务端数据发送完毕
    }
}
