/**
 * @Author: 江成军
 * @Date: 2018/12/19 16:19
 * @Description: 暂无
 */
package com.jcj.sparrow.NettyServerService;

import java.nio.charset.Charset;

/**
 *@Author 江成军
 *@Date 2018/12/19 16:19
 *@Description 消息的协议头类
 */
public class MessageHeader
{
    private final Charset charset=Charset.forName("utf-8");
    private byte magicType;
    private byte type;//消息类型  如：0xAF 表示心跳包
    private long requestId;//请求id
    private int length;
    private String body;

    public MessageHeader()
    {
    }

    public MessageHeader(byte magicType, byte type, long requestId, byte[] data)
    {
        this.magicType = magicType;
        this.type = type;
        this.requestId = requestId;
        this.length = data.length;
        this.body = new String(data, charset);
    }

    public MessageHeader(byte magicType, byte type, long requestId, String body)
    {
        this.magicType = magicType;
        this.type = type;
        this.requestId = requestId;
        this.length = body.getBytes(charset).length;
        this.body = body;
    }



    public byte getMagicType() {
        return magicType;
    }

    public void setMagicType(byte magicType) {
        this.magicType = magicType;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
