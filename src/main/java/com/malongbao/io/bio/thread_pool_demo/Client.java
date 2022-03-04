package com.malongbao.io.bio.thread_pool_demo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Description:
 * date: 2022/2/28 11:38
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);

        OutputStream outputStream = socket.getOutputStream();

        PrintStream printStream = new PrintStream(outputStream);

        //客户端发送的数据要跟服务端接收的数据一致，要不然数据就不可以被接收
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String str1 = scanner.nextLine();
                System.out.println("Client发送：" + str1);
                printStream.println(str1);
                printStream.flush();
            }
        }
    }
}
