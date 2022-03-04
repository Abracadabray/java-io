package com.malongbao.io.bio.thread_pool_demo;

import java.util.concurrent.*;

/**
 * Description:
 * date: 2022/2/28 17:33
 *
 * @author Hy
 * @since JDK 1.8
 */
@SuppressWarnings("all")
public class HandlerSocketServerPool {
    //创建一个线程池的成员变量用于存储一个线程池对象
    private ExecutorService executorService;


//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue)

    //初始化线程池对象
    public HandlerSocketServerPool(int maxThreadNum, int queueSize) {
        this.executorService = new ThreadPoolExecutor(3, maxThreadNum, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    //提供一个方法来提交任务给线程池的任务队列来暂存，等着线程来处理
    public void execute(Runnable target) {
        executorService.execute(target);
    }


}
