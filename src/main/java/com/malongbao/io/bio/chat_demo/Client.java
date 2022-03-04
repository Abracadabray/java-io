package com.malongbao.io.bio.chat_demo;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Description:
 * date: 2022/3/1 0:00
 *
 * @author Hy
 * @since JDK 1.8
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9999);
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Scanner scanner = new Scanner(System.in);
        String msg;
        new ClientReaderThread(socket).start();
        while (true) {
            msg = scanner.nextLine();
            printStream.println(msg);
            System.out.println("Client:" + msg);
            printStream.flush();
        }
    }
}
