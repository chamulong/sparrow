package com.jcj.sparrow.NettyServerService.protocol;

/**
 * @Author: 江成军
 * @Date: 2018/12/24 19:55
 * @Description: 0x04,辅助选点解析，具体内容部分(发出的命令内容、收到的应答内容)
 * 该协议目前暂时没用
 */
public class Radar_04 implements IMessageBody
{
    @Override
    public byte[] WriteToBytes()
    {
        return new byte[0];
    }

    @Override
    public void ReadFromBytes(byte[] messageBodyBytes)
    {

    }
}