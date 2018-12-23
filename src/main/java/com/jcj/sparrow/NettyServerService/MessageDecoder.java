package com.jcj.sparrow.NettyServerService;

import com.jcj.sparrow.NettyServerService.protocol.MessageFrame;
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

    private static final int HEADER_SIZE = 6;//应答头（2字节，0x3C3C）+命令字（1字节）+执行状态（1字节）+数据长度（2字节）

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength)
    {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    /**
     *@Author 江成军
     *@Description 根据接收协议进行解码，返回一个完整的协议包
     *@Date   2018.12.22
     *@Param
     *@return 完整的协议包
     **/
    @Override
    protected MessageFrame decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception
    {
        if (in == null){return null;}
        if (in.readableBytes() < HEADER_SIZE){return null;}//协议包总长度小于协议头
        in.markReaderIndex();
        short headerAnswer=in.readShort();
        if(headerAnswer!=15420){return null;}//应答头不是0x3C3C

        MessageFrame messageFrame=new MessageFrame();
        messageFrame.setCommandWord(in.readByte());
        messageFrame.setExecuteState(in.readByte());
        messageFrame.setDataLength(in.readShort());
        if (in.readableBytes() < messageFrame.getDataLength())//数据内容实际长度小于标明的长度
        {
            in.resetReaderIndex();
            return null;
        }

        messageFrame.setInfoContent(new byte[messageFrame.getDataLength()]);
        in.readBytes(messageFrame.getInfoContent());
        return messageFrame;
    }
}
