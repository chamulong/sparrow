package com.jcj.sparrow.NettyServerService;

import com.jcj.sparrow.NettyServerService.protocol.MessageFrame;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *@Author 江成军
 *@Date 2018/12/19 16:07
 *@Description 基于Netty的Server端
 */
@Component
public class NettyServer
{
    private Logger logger = LoggerFactory.getLogger(NettyServer.class);
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
                            cpl.addLast(new MessageDecoder(122880, 6, 2));
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

    private class ServerHandler extends SimpleChannelInboundHandler<MessageFrame>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MessageFrame msg) throws Exception
        {
            logger.info("server read msg:{}", msg);
            //MessageFrame resp = new MessageFrame(msg.getMagicType(), msg.getType(), msg.getRequestId(), "Hello world from server");
            //ctx.writeAndFlush(resp);
        }
    }



}
