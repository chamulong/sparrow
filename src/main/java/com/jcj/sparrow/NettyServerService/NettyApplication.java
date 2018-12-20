/**
 * @Author: 江成军
 * @Date: 2018/12/20 10:40
 * @Description: 暂无
 */
package com.jcj.sparrow.NettyServerService;

import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

/**
 *@Author 江成军
 *@Date 2018/12/20 10:40
 *@Description 配置SpringBoot启动时开始Netty服务
 */
@SpringBootApplication
public class NettyApplication implements CommandLineRunner
{
    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args)
    {
        SpringApplication.run(NettyApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        new NettyServer().bind(port);
    }
}
