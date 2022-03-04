public class Server {
//定义属性
private Selector selector;
private ServerSocketChannel ssChannel;
private static final int PORT = 9999;
//构造器
//初始化工作
public Server() {
try {
// 1、获取通道
ssChannel = ServerSocketChannel.open();
// 2、切换为非阻塞模式
ssChannel.configureBlocking(false);
// 3、绑定连接的端口
ssChannel.bind(new InetSocketAddress(PORT));
// 4、获取选择器Selector
selector = Selector.open();
// 5、将通道都注册到选择器上去，并且开始指定监听接收事件
ssChannel.register(selector , SelectionKey.OP_ACCEPT);
}catch (IOException e) {
e.printStackTrace();
}
}
//监听
public void listen() {
System.out.println("监听线程: " + Thread.currentThread().getName());
try {
while (selector.select() > 0){
System.out.println("开始一轮事件处理~~~");
// 7、获取选择器中的所有注册的通道中已经就绪好的事件
Iterator<SelectionKey> it = selector.selectedKeys().iterator();
// 8、开始遍历这些准备好的事件
while (it.hasNext()){
// 提取当前这个事件
SelectionKey sk = it.next();
// 9、判断这个事件具体是什么
if(sk.isAcceptable()){
// 10、直接获取当前接入的客户端通道
SocketChannel schannel = ssChannel.accept();
// 11 、切换成非阻塞模式
schannel.configureBlocking(false);
// 12、将本客户端通道注册到选择器
System.out.println(schannel.getRemoteAddress() + " 上线 ");
schannel.register(selector , SelectionKey.OP_READ);
//提示
}else if(sk.isReadable()){
//处理读 (专门写方法..)
readData(sk);
}

                    it.remove(); // 处理完毕之后需要移除当前事件
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //发生异常处理....

        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {
        //取到关联的channle
        SocketChannel channel = null;
        try {
           //得到channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if(count > 0) {
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("form 客户端: " + msg);
                //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
                sendInfoToOtherClients(msg, channel);
            }
        }catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                e.printStackTrace();
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            }catch (IOException e2) {
                e2.printStackTrace();;
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self ) throws  IOException{
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for(SelectionKey key: selector.keys()) {
            //通过 key  取出对应的 SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        Server groupChatServer = new Server();
        groupChatServer.listen();
    }
}



public class Client {
//定义相关的属性
private final String HOST = "127.0.0.1"; // 服务器的ip
private final int PORT = 9999; //服务器端口
private Selector selector;
private SocketChannel socketChannel;
private String username;

    //构造器, 完成初始化工作
    public Client() throws IOException {

        selector = Selector.open();
        //连接服务器
        socketChannel = socketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");

    }

    //向服务器发送消息
    public void sendInfo(String info) {
        info = username + " 说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取从服务器端回复的消息
    public void readInfo() {
        try {

            int readChannels = selector.select();
            if(readChannels > 0) {//有可以用的通道

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        //得到相关的通道
                       SocketChannel sc = (SocketChannel) key.channel();
                       //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove(); //删除当前的selectionKey, 防止重复操作
            } else {
                //System.out.println("没有可以用的通道...");

            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //启动我们客户端
        Client chatClient = new Client();
        //启动一个线程, 每个3秒，读取从服务器发送数据
        new Thread() {
            public void run() {

                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}



