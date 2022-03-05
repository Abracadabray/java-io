实例要求：使用 `IDEA` 创建 `Netty` 项目

1. `Netty` 服务器在 `6668` 端口监听，客户端能发送消息给服务器"hello,服务器~"
2. 服务器可以回复消息给客户端"hello,客户端~"
3. 目的：对 `Netty` 线程模型有一个初步认识，便于理解 `Netty` 模型理论
4.
    1. 编写服务端
    2. 编写客户端
    3. 对 `netty` 程序进行分析，看看 `netty` 模型特点
    4. 说明：创建 `Maven` 项目，并引入 `Netty` 包


> **<font color='red'>任务队列中的 Task 有 3 种典型使用场景:ChannelInboundHandlerAdapter</font>**

1. 用户程序自定义的普通任务
2. 用户自定义定时任务
3. 非当前 `Reactor` 线程调用 `Channel` 的各种方法
   例如在**推送系统**的业务线程里面，根据用户的标识，找到对应的 `Channel` 引用，然后调用 `Write` 类方法向该用户推送消息，就会进入到这种场景。最终的 `Write` 会提交到任务队列中后被异步消费



> **<font color='red'>方案再说明</font>**

1. `Netty` 抽象出两组线程池，`BossGroup` 专门负责接收客户端连接，`WorkerGroup` 专门负责网络读写操作。
2. `NioEventLoop` 表示一个不断循环执行处理任务的线程，每个 `NioEventLoop` 都有一个 `Selector`，用于监听绑定在其上的 `socket`网络通道。
3. `NioEventLoop` 内部采用串行化设计，从消息的 **读取->解码->处理->编码->发送**，始终由 `IO` 线程 `NioEventLoop` 负责

- `NioEventLoopGroup` 下包含多个 `NioEventLoop`

- 每个 `NioEventLoop` 中包含有一个 `Selector`，一个 `taskQueue`
- 每个 `NioEventLoop` 的 `Selector` 上可以注册监听多个 `NioChannel`
- 每个 `NioChannel` 只会绑定在唯一的 `NioEventLoop` 上
- 每个 `NioChannel` 都绑定有一个自己的 `ChannelPipeline`