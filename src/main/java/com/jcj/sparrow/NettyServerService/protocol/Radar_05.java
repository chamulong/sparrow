package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Author: 江成军
 * @Date: 2018/12/25 9:37
 * @Description: 0x05，测量，具体内容部分(发出的命令内容、收到的应答内容)
 * 测量和预览的协议除了命令字不一样以外，其它都一样
 */
public class Radar_05 implements IMessageBody
{
    // 脉压结果数据长度(单位：点)
    public float PusleData_Length;

    // 脉压结果的距离采样间隔（单位：米）
    public float DistanceSampling_Interval;

    // 观测点数量(单位:点)
    public float ObservationPoint_Num;

    // 每个观测点测量次数（单位：次）
    public float CountPer_Observation;

    // 形变数据的时间采样间隔(单位：秒)
    public float TimeSampling_Interval;

    // 环境参数点数(单位：点，范围：0~10)
    public float EnviromentalParam_Num;

    // 环境数据
    public float[] Enviromental_Data;

    // 脉冲幅度
    public float[] Pulse_Amplitude;

    // 目标观测点形变量（单位：mm）,行为观测次数，列为观测目标数
    public float[][] Target_Deformation;

    public Radar_05()
    {
        PusleData_Length = 0f;
        DistanceSampling_Interval = 0f;
        ObservationPoint_Num = 0f;
        CountPer_Observation = 0f;
        TimeSampling_Interval = 0f;
        EnviromentalParam_Num = 0f;
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
        PusleData_Length = buff.readFloat();
        DistanceSampling_Interval = buff.readFloat();
        ObservationPoint_Num = buff.readFloat();
        CountPer_Observation = buff.readFloat();
        TimeSampling_Interval = buff.readFloat();
        EnviromentalParam_Num = buff.readFloat();

        //环境数据
        Enviromental_Data = new float[(int)EnviromentalParam_Num];
        for (int i = 0; i < EnviromentalParam_Num; i++)
        {
            Enviromental_Data[i] = buff.readFloat();
        }

        //脉压数据
        Pulse_Amplitude = new float[(int)PusleData_Length];
        for (int i = 0; i < PusleData_Length; i++)
        {
            Pulse_Amplitude[i] = buff.readFloat();
        }

        //形变数据
        Target_Deformation = new float[(int)CountPer_Observation][(int)ObservationPoint_Num];
        for (int i = 0; i < CountPer_Observation; i++)
        {
            for (int j = 0; j < ObservationPoint_Num; j++)
            {
                Target_Deformation[i][j] =buff.readFloat();
            }
        }
    }

    public float getPusleData_Length() {
        return PusleData_Length;
    }

    public void setPusleData_Length(float pusleData_Length) {
        PusleData_Length = pusleData_Length;
    }

    public float getDistanceSampling_Interval() {
        return DistanceSampling_Interval;
    }

    public void setDistanceSampling_Interval(float distanceSampling_Interval) {
        DistanceSampling_Interval = distanceSampling_Interval;
    }

    public float getObservationPoint_Num() {
        return ObservationPoint_Num;
    }

    public void setObservationPoint_Num(float observationPoint_Num) {
        ObservationPoint_Num = observationPoint_Num;
    }

    public float getCountPer_Observation() {
        return CountPer_Observation;
    }

    public void setCountPer_Observation(float countPer_Observation) {
        CountPer_Observation = countPer_Observation;
    }

    public float getTimeSampling_Interval() {
        return TimeSampling_Interval;
    }

    public void setTimeSampling_Interval(float timeSampling_Interval) {
        TimeSampling_Interval = timeSampling_Interval;
    }

    public float getEnviromentalParam_Num() {
        return EnviromentalParam_Num;
    }

    public void setEnviromentalParam_Num(float enviromentalParam_Num) {
        EnviromentalParam_Num = enviromentalParam_Num;
    }

    public float[] getEnviromental_Data() {
        return Enviromental_Data;
    }

    public void setEnviromental_Data(float[] enviromental_Data) {
        Enviromental_Data = enviromental_Data;
    }

    public float[] getPulse_Amplitude() {
        return Pulse_Amplitude;
    }

    public void setPulse_Amplitude(float[] pulse_Amplitude) {
        Pulse_Amplitude = pulse_Amplitude;
    }

    public float[][] getTarget_Deformation() {
        return Target_Deformation;
    }

    public void setTarget_Deformation(float[][] target_Deformation) {
        Target_Deformation = target_Deformation;
    }
}