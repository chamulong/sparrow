package com.jcj.sparrow.NettyServerService.protocol;

/**
 * @Author 江成军
 * @Date 2018/12/23 16:36
 * @Description 通信协议的读、写接口
 */
public interface IMessageBody
{
    byte[] WriteToBytes();
    void ReadFromBytes(byte[] messageBodyBytes);
}
