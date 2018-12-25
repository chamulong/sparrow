package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Author: 江成军
 * @Date: 2018/12/25 9:43
 * @Description: 0x06，结束预览/测量，具体内容部分(发出的命令内容、收到的应答内容)
 */
public class Radar_06 implements IMessageBody
{
    @Override
    public byte[] WriteToBytes()
    {
        ByteBuf buff= ByteBufAllocator.DEFAULT.ioBuffer();
        buff.writeShort(0);
        return buff.array();
    }

    @Override
    public void ReadFromBytes(byte[] messageBodyBytes)
    {

    }
}