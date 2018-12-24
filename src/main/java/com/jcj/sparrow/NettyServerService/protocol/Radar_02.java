package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Author: 江成军
 * @Date: 2018/12/24 15:59
 * @Description: 0x02，心跳包，具体内容部分(发出的命令内容、收到的应答内容)
 */
public class Radar_02 implements IMessageBody
{
    // 硬件状态，系统状态(正常/异常)
    private String HardwareStatus_System;

    // 硬件状态，串口状态(正常/异常)
    private String HardwareStatus_Com;

    // 硬件状态，RF板状态(正常/异常)
    private String HardwareStatus_RF;

    // 电池电量状态
    private int BatteryStatus;

    // 处理机状态(正常/异常)
    private String ProcessorStatus;

    // 当前工作状态(待机状态/预览状态/测量状态)
    private String NowWorkStatus;

    // 硬件姿态，俯仰倾角（单位：度）
    private float Attitude_Pitch;

    // 硬件姿态，横滚角（单位：度）
    private float Attitude_Roll;

    // 硬件姿态，方位角（单位：度）
    private float Attitude_Azimuth;

    public Radar_02()
    {
        HardwareStatus_System = "正常";
        HardwareStatus_Com = "正常";
        HardwareStatus_RF = "正常";
        BatteryStatus = 0;
        ProcessorStatus = "正常";
        NowWorkStatus = "未知";
        Attitude_Pitch = 0f;
        Attitude_Roll = 0f;
        Attitude_Azimuth = 0f;
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
        byte[] arrContent=new byte[5];
        buff.readBytes(arrContent);
        if ((arrContent[0] & 0x80) == 0x80) { HardwareStatus_System = "异常"; }
        if ((arrContent[0] & 0x40) == 0x40) { HardwareStatus_Com = "异常"; }
        if ((arrContent[0] & 0x20) == 0x20) { HardwareStatus_RF = "异常"; }

        BatteryStatus = (int)arrContent[1];

        if ((arrContent[2] & 0x80) == 0x80) { ProcessorStatus = "异常"; }

        int intNowWorkStatus = (int)arrContent[4];
        if (intNowWorkStatus == 0) { NowWorkStatus = "待机状态"; }
        else if (intNowWorkStatus == 1) { NowWorkStatus = "预览状态"; }
        else if (intNowWorkStatus == 2) { NowWorkStatus = "测量状态"; }

        Attitude_Pitch =buff.readFloat();
        Attitude_Roll = buff.readFloat();
        Attitude_Azimuth = buff.readFloat();

        buff.readBytes(7);
    }

    public String getHardwareStatus_System() {
        return HardwareStatus_System;
    }

    public void setHardwareStatus_System(String hardwareStatus_System) {
        HardwareStatus_System = hardwareStatus_System;
    }

    public String getHardwareStatus_Com() {
        return HardwareStatus_Com;
    }

    public void setHardwareStatus_Com(String hardwareStatus_Com) {
        HardwareStatus_Com = hardwareStatus_Com;
    }

    public String getHardwareStatus_RF() {
        return HardwareStatus_RF;
    }

    public void setHardwareStatus_RF(String hardwareStatus_RF) {
        HardwareStatus_RF = hardwareStatus_RF;
    }

    public int getBatteryStatus() {
        return BatteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        BatteryStatus = batteryStatus;
    }

    public String getProcessorStatus() {
        return ProcessorStatus;
    }

    public void setProcessorStatus(String processorStatus) {
        ProcessorStatus = processorStatus;
    }

    public String getNowWorkStatus() {
        return NowWorkStatus;
    }

    public void setNowWorkStatus(String nowWorkStatus) {
        NowWorkStatus = nowWorkStatus;
    }

    public float getAttitude_Pitch() {
        return Attitude_Pitch;
    }

    public void setAttitude_Pitch(float attitude_Pitch) {
        Attitude_Pitch = attitude_Pitch;
    }

    public float getAttitude_Roll() {
        return Attitude_Roll;
    }

    public void setAttitude_Roll(float attitude_Roll) {
        Attitude_Roll = attitude_Roll;
    }

    public float getAttitude_Azimuth() {
        return Attitude_Azimuth;
    }

    public void setAttitude_Azimuth(float attitude_Azimuth) {
        Attitude_Azimuth = attitude_Azimuth;
    }
}