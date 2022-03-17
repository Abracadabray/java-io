### 调用过程



1. `ClientBootstrap#main`发起调用
2. 走到下面这一行代码后

```java
 HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
```

3. 调用`NettyClient#getBean`，在此方法里与服务端建立链接。

4. 于是就执行`NettyClientHandler#channelActive`

5. 接着回到`NettyClient#getBean`调用`NettyClientHandler#setPara`，调用完之后再回到`NettyClient#getBean`，用线程池提交任务

6. 因为用线程池提交了任务，就准备执行`NettyClientHandler#call`线程任务

7. 在`NettyClientHandler#call`中发送数据给服务提供者

   ```java
   context.writeAndFlush(para);
   ```

   由于还没收到服务提供者的数据结果，所以wait住

8. 来到了服务提供者这边，从Socket通道中收到了数据，所以执行`NettyServerHandler#channelRead`，然后因为此方法中执行了

   ```java
   String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
   ```

9. 就去`HelloServiceImpl#hello`中执行业务逻辑，返回数据给`NettyServerHandler#channelRead`，`NettyServerHandler#channelRead`再把数据发给客户端

10. `NettyClientHandler#channelRead`收到服务提供者发来的数据，唤醒之前wait的线程

11. 所以之前wait的线程从`NettyClientHandler#call`苏醒，返回result给`NettyClient#getBean`

12. `NettyClient#getBean`get()到数据，`ClientBootstrap#main`中的此函数调用返回，得到服务端提供的数据。

    ```java
     String res = service.hello("你好 dubbo~");
    ```

13.至此，一次RPC调用结束。