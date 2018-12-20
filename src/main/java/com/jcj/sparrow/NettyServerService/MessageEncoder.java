/**
 * @Author: 江成军
 * @Date: 2018/12/19 16:40
 * @Description: 暂无
 */
package com.jcj.sparrow.NettyServerService;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 *@Author 江成军
 *@Date 2018/12/19 16:40
 *@Description 编码器
 */
public class MessageEncoder extends MessageToByteEncoder<MessageHeader>
{
    private final Charset charset = Charset.forName("utf-8");

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageHeader msg, ByteBuf out) throws Exception
    {
        out.writeByte(msg.getMagicType());
        out.writeByte(msg.getType());
        out.writeLong(msg.getRequestId());

        byte[] data = msg.getBody().getBytes(charset);
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
