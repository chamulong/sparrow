/**
 * @Author: 江成军
 * @Date: 2018/12/19 16:07
 * @Description: 暂无
 */
package com.jcj.sparrow.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@Author 江成军
 *@Date 2018/12/19 16:07
 *@Description 基于Netty的Server端
 */
public class NettyServer
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public void bind(int port) throws InterruptedException
    {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try
        {
            ServerBootstrap sbs=new ServerBootstrap();
            sbs.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline cpl=channel.pipeline();
                            cpl.addLast(new MessageDecoder(1<<20, 10, 4));
                            cpl.addLast(new MessageEncoder());
                            cpl.addLast(new ServerHandler());
                        }
                    });
            ChannelFuture future=sbs.bind(port).sync();//绑定端口，响应连接请求就绪
            logger.info("server bind port:{}", port);
            future.channel().closeFuture().sync();
        }
        finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ServerHandler extends SimpleChannelInboundHandler<MessageHeader>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MessageHeader msg) throws Exception
        {
            logger.info("server read msg:{}", msg);
            MessageHeader resp = new MessageHeader(msg.getMagicType(), msg.getType(), msg.getRequestId(), "Hello world from server");
            ctx.writeAndFlush(resp);
        }
    }



}
