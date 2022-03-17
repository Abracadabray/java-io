package com.malongbao.io.netty_rpc_demo;

/**
 * Description:
 * date: 2022/3/17 17:40
 *
 * @author Hy
 * @since JDK 1.8
 */
//这个是接口，是服务提供方和 服务消费方都需要
public interface HelloService {

    String hello(String mes);
}
