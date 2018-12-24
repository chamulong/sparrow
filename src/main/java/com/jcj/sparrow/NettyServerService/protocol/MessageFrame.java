/**
 * @Author: 江成军
 * @Date: 2018/12/19 16:19
 * @Description: 暂无
 */
package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 *@Author 江成军
 *@Date 2018/12/19 16:19
 *@Description 消息的协议头类
 * 基于RKDM-1D软件接口协议
 * 发送数据的标准格式为：命令头（2字节，0x5A5A）+命令字（1字节）+数据长度（2字节）+数据内容（N字节）
 * 接收数据的格式：应答头（2字节，0x3C3C）+命令字（1字节）+执行状态（1字节）+数据长度（2字节）+数据内容（N字节）
 */
public class MessageFrame
{
    //private final Charset charset=Charset.forName("utf-8");
    private short headerCommand=23130;//命令头,0x5A5A，2字节
    private short headerAnswer=15420;//应答头,0x3C3C，2字节
    private byte commandWord;//命令字，1字节
    private byte executeState;//执行状态，1字节
    private short dataLength;//数据长度，2字节
    private byte[] infoContent;//数据内容

   /**
   *@Author 江成军
   *@Description 组装发送包
   *@return 字节数组
   **/
    public byte[] WriteToBytes()
    {
        ByteBuf buff= ByteBufAllocator.DEFAULT.ioBuffer();
        buff.writeShort(headerCommand);
        buff.writeByte(commandWord);
        buff.writeShort((short)infoContent.length);
        buff.writeBytes(infoContent);
        return buff.array();
    }

    /**
    *@Author 江成军
    *@Description 解析数据包，提取内容
    *@Param 完整的一个协议包
    *@return 无
    **/
    public void ReadFromBytes(byte[] headerBytes)
    {
        ByteBuf buff = Unpooled.copiedBuffer(headerBytes);
        short tempHeaderAnswer =buff.readShort();
        if (headerAnswer == tempHeaderAnswer)//应答头是“0x3C3C”才进行进一步解析
        {
            commandWord=buff.readByte();
            executeState=buff.readByte();
            if (executeState == 0)//状态为‘正确执行’才进行内容的解析
            {
                dataLength=buff.readShort();
                infoContent=new byte[dataLength];
                buff.readBytes(infoContent);
            }
        }
    }


    public short getHeaderCommand() {
        return headerCommand;
    }

    public short getHeaderAnswer() {
        return headerAnswer;
    }

    public byte getCommandWord() {
        return commandWord;
    }

    public void setCommandWord(byte commandWord) {
        this.commandWord = commandWord;
    }

    public byte getExecuteState() {
        return executeState;
    }

    public void setExecuteState(byte executeState) {
        this.executeState = executeState;
    }

    public short getDataLength() {
        return dataLength;
    }

    public void setDataLength(short dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(byte[] infoContent) {
        this.infoContent = infoContent;
    }
}
