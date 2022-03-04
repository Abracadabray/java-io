package com.malongbao.io.nio.nio_base_demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Description: 服务端接收客户端的连接请求，并接收多个客户端发送过来的事件
 * date: 2022/3/2 17:59
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//1. 获取通道
        serverSocketChannel.configureBlocking(false); //2. 切换非阻塞模式
        serverSocketChannel.bind(new InetSocketAddress(9999));//3. 绑定连接
        Selector selector = Selector.open();//4. 获取选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//5. 将通道注册到选择器上, 并且指定“监听接收事件”
        while (selector.select() > 0) { //6. 轮询式的获取选择器上已经“准备就绪”的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();//7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next(); //8. 获取准备“就绪”的是事件
                if (selectionKey.isAcceptable()) {//9. 判断事件状态是否准备就绪
                    SocketChannel socketChannel = serverSocketChannel.accept(); //10. 若“接收就绪”，获取客户端连接
                    socketChannel.configureBlocking(false); //11. 切换非阻塞模式
                    socketChannel.register(selector, SelectionKey.OP_READ);//12. 将该通道注册到选择器上
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();//13. 获取当前选择器上“读就绪”状态的通道
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//14. 读取数据
                    int len = 0;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }

                }
                it.remove();//15. 取消选择键 SelectionKey
            }
        }
    }
}
