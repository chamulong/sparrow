package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Author: 江成军
 * @Date: 2018/12/23 22:18
 * @Description: 0x00，参数设置(发出的命令内容、收到的应答内容)
 */
public class Radar_00 implements IMessageBody
{
    // 雷达核心参数，起始频率(单位：MHz)
    private float RCP_Startfreq;

    // 雷达核心参数，终止频率(单位：MHz)
    private float RCP_Endfreq;

    // 雷达核心参数，脉冲宽度(单位：us)
    private float RCP_Pulsewidth;

    // 雷达核心参数，重复频率(单位：MHz)
    private float RCP_Repeatedfreq;

    // 数字采集参数，采样频率(单位：MHz)
    private float DAP_Samplefreq;

    // 数字采集参数，通道增益(单位：dB)
    private float DAP_Channelgain;

    // 数字采集参数，通道延迟(单位：个周期)
    private float DAP_Channeldelay;

    // 数字采集参数，采样点数(单位：个)
    private float DAP_Samplepoints;

    // 布站观测参数，目标类型(桥/塔)
    private String CSO_Targettype;

    // 布站观测参数，最近距离(单位：米)
    private float CSO_Obsernearestdistance;

    // 布站观测参数，最远距离(单位：米)
    private float CSO_Obsermaximumdistance;

    // 布站观测参数，降噪指数(无/弱/中/强)
    private String CSO_Noisereductionindex;

    // 布站观测参数，选点间隔(单位：米)
    private float CSO_Pointinterval;

    // 布站观测参数，目标垂直距离(单位：米)
    private float CSO_Targetverticaldistance;

    // 布站观测参数，环境校正(无/GCP校正/大气参数校正/自适应补偿)
    private String CSO_Environmentalcorrection;

    // GCP参数，数量(单位：个)
    private float GCP_Num;

    // GCP参数，距离(单位：米)
    private float[] GCP_Distance;

    // GCP参数，视角（单位：度）
    private float[] GCP_Visualangle;

    // 观测目标参数，数量（单位：个）
    private float OOP_Num;

    // 观测目标参数，距离（单位：米）
    private float[] OOP_Distance;

    // 观测目标参数，视角（单位：度）
    private float[] OOP_Visualangle;

    public Radar_00()
    {
        RCP_Startfreq = 24000f;
        RCP_Endfreq = 25000f;
        RCP_Pulsewidth = 2000f;

        //设置重复频率（采样频率，默认200Hz）
        RCP_Repeatedfreq = 200f;

        DAP_Samplefreq = 10f;
        DAP_Channelgain = 0f;
        DAP_Channeldelay = 100f;
        DAP_Samplepoints = 16384f;

        CSO_Targettype = "桥";
        CSO_Obsernearestdistance = 0f;
        CSO_Obsermaximumdistance = 100f;
        CSO_Noisereductionindex = "无";
        CSO_Pointinterval = 1f;
        CSO_Targetverticaldistance = 10f;
        CSO_Environmentalcorrection = "无";

        GCP_Num = 0f;

        OOP_Num = 0f;
    }


    @Override
    public byte[] WriteToBytes()
    {
        ByteBuf buff= ByteBufAllocator.DEFAULT.ioBuffer();
        buff.writeFloat(RCP_Startfreq);
        buff.writeFloat(RCP_Endfreq);
        buff.writeFloat(RCP_Pulsewidth);
        buff.writeFloat(RCP_Repeatedfreq);
        buff.writeFloat(0f);
        buff.writeFloat(0f);
        buff.writeFloat(0f);
        buff.writeFloat(0f);

        buff.writeFloat(DAP_Samplefreq);
        buff.writeFloat(DAP_Channelgain);
        buff.writeFloat(DAP_Channeldelay);
        buff.writeFloat(DAP_Samplepoints);
        buff.writeFloat(0f);
        buff.writeFloat(0f);
        buff.writeFloat(0f);
        buff.writeFloat(0f);

        buff.writeFloat(CSO_Targettype.equals("桥") ? 0 : 1);
        buff.writeFloat(CSO_Obsernearestdistance);
        buff.writeFloat(CSO_Obsermaximumdistance);
        float noisereductionindex = 0;
        if (CSO_Noisereductionindex.equals("弱")){noisereductionindex = 1;}
        else if (CSO_Noisereductionindex.equals("中")){noisereductionindex = 2;}
        else if (CSO_Noisereductionindex.equals("强")){noisereductionindex = 3;}
        buff.writeFloat(noisereductionindex);
        buff.writeFloat(CSO_Pointinterval);
        buff.writeFloat(CSO_Targetverticaldistance);
        float environmentalcorrection = 0;
        if (CSO_Environmentalcorrection.equals("GCP校正")){environmentalcorrection = 1;}
        else if (CSO_Environmentalcorrection.equals("大气参数校准")){environmentalcorrection = 2;}
        else if (CSO_Environmentalcorrection.equals("自适应补偿")) { environmentalcorrection = 3; }
        buff.writeFloat(environmentalcorrection);
        buff.writeFloat(0f);

        //GCP
        buff.writeFloat(GCP_Num);
        buff.writeFloat(0f);
        for (int i = 0; i < GCP_Num; i++)
        {
            buff.writeFloat(GCP_Distance[i]);
            buff.writeFloat(GCP_Visualangle[i]);
        }
        int intOtherGCP = 15 - (int)GCP_Num;
        for (int i = 0; i < intOtherGCP; i++)
        {
            buff.writeFloat(0f);
            buff.writeFloat(0f);
        }

        //观测目标参数
        buff.writeFloat(OOP_Num);
        buff.writeFloat(0f);
        for (int i = 0; i < OOP_Num; i++)
        {
            buff.writeFloat(OOP_Distance[i]);
            buff.writeFloat(OOP_Visualangle[i]);
        }
        int intOtherTarget = 99 - (int)OOP_Num;
        for (int i = 0; i < intOtherTarget; i++)
        {
            buff.writeFloat(0f);
            buff.writeFloat(0f);
        }

        return buff.array();
    }

    @Override
    public void ReadFromBytes(byte[] messageBodyBytes)
    {
        if (messageBodyBytes==null)
        {
            return;
        }
    }


    public float getRCP_Startfreq() {
        return RCP_Startfreq;
    }

    public void setRCP_Startfreq(float RCP_Startfreq) {
        this.RCP_Startfreq = RCP_Startfreq;
    }

    public float getRCP_Endfreq() {
        return RCP_Endfreq;
    }

    public void setRCP_Endfreq(float RCP_Endfreq) {
        this.RCP_Endfreq = RCP_Endfreq;
    }

    public float getRCP_Pulsewidth() {
        return RCP_Pulsewidth;
    }

    public void setRCP_Pulsewidth(float RCP_Pulsewidth) {
        this.RCP_Pulsewidth = RCP_Pulsewidth;
    }

    public float getRCP_Repeatedfreq() {
        return RCP_Repeatedfreq;
    }

    public void setRCP_Repeatedfreq(float RCP_Repeatedfreq) {
        this.RCP_Repeatedfreq = RCP_Repeatedfreq;
    }

    public float getDAP_Samplefreq() {
        return DAP_Samplefreq;
    }

    public void setDAP_Samplefreq(float DAP_Samplefreq) {
        this.DAP_Samplefreq = DAP_Samplefreq;
    }

    public float getDAP_Channelgain() {
        return DAP_Channelgain;
    }

    public void setDAP_Channelgain(float DAP_Channelgain) {
        this.DAP_Channelgain = DAP_Channelgain;
    }

    public float getDAP_Channeldelay() {
        return DAP_Channeldelay;
    }

    public void setDAP_Channeldelay(float DAP_Channeldelay) {
        this.DAP_Channeldelay = DAP_Channeldelay;
    }

    public float getDAP_Samplepoints() {
        return DAP_Samplepoints;
    }

    public void setDAP_Samplepoints(float DAP_Samplepoints) {
        this.DAP_Samplepoints = DAP_Samplepoints;
    }

    public String getCSO_Targettype() {
        return CSO_Targettype;
    }

    public void setCSO_Targettype(String CSO_Targettype) {
        this.CSO_Targettype = CSO_Targettype;
    }

    public float getCSO_Obsernearestdistance() {
        return CSO_Obsernearestdistance;
    }

    public void setCSO_Obsernearestdistance(float CSO_Obsernearestdistance) {
        this.CSO_Obsernearestdistance = CSO_Obsernearestdistance;
    }

    public float getCSO_Obsermaximumdistance() {
        return CSO_Obsermaximumdistance;
    }

    public void setCSO_Obsermaximumdistance(float CSO_Obsermaximumdistance) {
        this.CSO_Obsermaximumdistance = CSO_Obsermaximumdistance;
    }

    public String getCSO_Noisereductionindex() {
        return CSO_Noisereductionindex;
    }

    public void setCSO_Noisereductionindex(String CSO_Noisereductionindex) {
        this.CSO_Noisereductionindex = CSO_Noisereductionindex;
    }

    public float getCSO_Pointinterval() {
        return CSO_Pointinterval;
    }

    public void setCSO_Pointinterval(float CSO_Pointinterval) {
        this.CSO_Pointinterval = CSO_Pointinterval;
    }

    public float getCSO_Targetverticaldistance() {
        return CSO_Targetverticaldistance;
    }

    public void setCSO_Targetverticaldistance(float CSO_Targetverticaldistance) {
        this.CSO_Targetverticaldistance = CSO_Targetverticaldistance;
    }

    public String getCSO_Environmentalcorrection() {
        return CSO_Environmentalcorrection;
    }

    public void setCSO_Environmentalcorrection(String CSO_Environmentalcorrection) {
        this.CSO_Environmentalcorrection = CSO_Environmentalcorrection;
    }

    public float getGCP_Num() {
        return GCP_Num;
    }

    public void setGCP_Num(float GCP_Num) {
        this.GCP_Num = GCP_Num;
    }

    public float[] getGCP_Distance() {
        return GCP_Distance;
    }

    public void setGCP_Distance(float[] GCP_Distance) {
        this.GCP_Distance = GCP_Distance;
    }

    public float[] getGCP_Visualangle() {
        return GCP_Visualangle;
    }

    public void setGCP_Visualangle(float[] GCP_Visualangle) {
        this.GCP_Visualangle = GCP_Visualangle;
    }

    public float getOOP_Num() {
        return OOP_Num;
    }

    public void setOOP_Num(float OOP_Num) {
        this.OOP_Num = OOP_Num;
    }

    public float[] getOOP_Distance() {
        return OOP_Distance;
    }

    public void setOOP_Distance(float[] OOP_Distance) {
        this.OOP_Distance = OOP_Distance;
    }

    public float[] getOOP_Visualangle() {
        return OOP_Visualangle;
    }

    public void setOOP_Visualangle(float[] OOP_Visualangle) {
        this.OOP_Visualangle = OOP_Visualangle;
    }
}