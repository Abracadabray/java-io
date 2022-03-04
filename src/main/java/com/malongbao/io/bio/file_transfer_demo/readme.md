-- 服务端运行结果（一直运行）
服务端接收文件类型：.png
java.net.SocketException: Connection reset
at java.net.SocketInputStream.read(SocketInputStream.java:209)
at java.net.SocketInputStream.read(SocketInputStream.java:141)
at java.io.DataInputStream.read(DataInputStream.java:100)
at ServerReaderThread.run(ServerReaderThread.java:34)

-- 客户端发送文件之后就停止，服务端还在一直进行连接等待；

-------------------------------------------------------------
socket.shutdownOutput();//socket关闭输出流
-- 客户端发送文件之后就停止，服务端检测到后取消等待；（len = dataInputStream.read(buffer))= -1


