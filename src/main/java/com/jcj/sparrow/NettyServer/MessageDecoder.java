/**
 * @Author: 江成军
 * @Date: 2018/12/19 16:31
 * @Description: 暂无
 */
package com.jcj.sparrow.NettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *@Author 江成军
 *@Date 2018/12/19 16:31
 *@Description 基于LengthFieldBasedFrameDecoder的解码器，该解码器自动屏蔽TCP底层的半包和粘包问题。
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final int HEADER_SIZE = 14;//头部信息的大小应该是 byte+byte+int = 1+1+8+4 = 14

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength)
    {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception
    {
        if (in == null)
        {
            return null;
        }

        if (in.readableBytes() <= HEADER_SIZE)
        {
            return null;
        }

        in.markReaderIndex();

        byte magic = in.readByte();
        byte type = in.readByte();
        long requestId = in.readLong();
        int dataLength = in.readInt();

        if (in.readableBytes() < dataLength)
        {
            in.resetReaderIndex();
            return null;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        String body = new String(data, "UTF-8");
        MessageHeader msg = new MessageHeader(magic, type, requestId, body);
        return msg;
    }
}
