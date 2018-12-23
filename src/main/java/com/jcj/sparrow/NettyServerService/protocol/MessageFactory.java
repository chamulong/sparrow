package com.jcj.sparrow.NettyServerService.protocol;

import com.jcj.sparrow.NettyServerService.tool.ClassUtils;

/**
 * @Author: 江成军
 * @Date: 2018/12/23 19:27
 * @Description: 协议工厂类，利用反射机制，动态加载相应的协议
 */
public class MessageFactory
{
    /**
    *@Author 江成军
    *@Description 根据命令字和字节内容，动态创建类的实例
    *@Param messageType：命令字，messageBodyBytes：字节内容
    *@return  相应的协议实例
    **/
    public static IMessageBody Create(int messageType, byte[] messageBodyBytes)
    {
        String nameSpace = MessageFactory.class.getPackage().getName();
        String className = nameSpace + ".Radar_" +String.format("%02d", messageType);//其中格式说明（0代表前面补充0，2代表长度为2，d代表参数为整数型，如1输出为‘01’）
        Object messageBody = ClassUtils.getBean(className);
        if (messageBody != null)
        {
            IMessageBody msg = (IMessageBody)messageBody;
            msg.ReadFromBytes(messageBodyBytes);
            return msg;
        }
        return null;
    }
}