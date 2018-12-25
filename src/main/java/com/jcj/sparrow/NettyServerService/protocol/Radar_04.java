package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Author: 江成军
 * @Date: 2018/12/24 19:55
 * @Description: 0x04,辅助选点解析，具体内容部分(发出的命令内容、收到的应答内容)
 * 该协议目前暂时没用
 */
public class Radar_04 implements IMessageBody
{

    // 目标数量（单位：个）
    public int TargetNum;

    // 目标距离(单位：米)
    public float[] TargetDistance;

    public Radar_04()
    {
        TargetNum = 0;
    }

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
        ByteBuf buff = Unpooled.copiedBuffer(messageBodyBytes);
        TargetNum = (int)buff.readFloat();
        TargetDistance = new float[TargetNum];
        for (int i = 0; i < TargetNum; i++)
        {
            TargetDistance[i] = buff.readFloat();
        }
        int otherdata = (99 - TargetNum) * 4;
        buff.readBytes(otherdata);
    }

    public int getTargetNum() {
        return TargetNum;
    }

    public void setTargetNum(int targetNum) {
        TargetNum = targetNum;
    }

    public float[] getTargetDistance() {
        return TargetDistance;
    }

    public void setTargetDistance(float[] targetDistance) {
        TargetDistance = targetDistance;
    }
}