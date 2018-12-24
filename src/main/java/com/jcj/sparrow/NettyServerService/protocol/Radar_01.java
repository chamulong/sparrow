package com.jcj.sparrow.NettyServerService.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Author: 江成军
 * @Date: 2018/12/24 10:45
 * @Description: 0x01，查询设备参数，具体内容部分(发出的命令内容、收到的应答内容)
 */
public class Radar_01 implements IMessageBody
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

    // GCP参数，数量(单位：个，范围：0~15)
    private float GCP_Num;

    // GCP参数，距离(单位：米)
    private float[] GCP_Distance;

    // GCP参数，视角（单位：度）
    private float[] GCP_Visualangle;

    // 观测目标参数，数量（单位：个，范围：0~99）
    private float OOP_Num;

    // 观测目标参数，距离（单位：米）
    private float[] OOP_Distance;

    // 观测目标参数，视角（单位：度）
    private float[] OOP_Visualangle;

    public Radar_01()
    {
        RCP_Startfreq = 0f;
        RCP_Endfreq = 0f;
        RCP_Pulsewidth = 0f;
        RCP_Repeatedfreq = 0f;

        DAP_Samplefreq = 0f;
        DAP_Channelgain = 0f;
        DAP_Channeldelay = 0f;
        DAP_Samplepoints = 0f;

        CSO_Targettype = "桥";
        CSO_Obsernearestdistance = 0f;
        CSO_Obsermaximumdistance = 0f;
        CSO_Noisereductionindex = "无";
        CSO_Pointinterval = 0f;
        CSO_Targetverticaldistance = 0f;
        CSO_Environmentalcorrection = "无";

        GCP_Num = 0f;

        OOP_Num = 0f;
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
        //雷达核心参数
        RCP_Startfreq =buff.readFloat();
        RCP_Endfreq = buff.readFloat();
        RCP_Pulsewidth = buff.readFloat();
        RCP_Repeatedfreq = buff.readFloat();
        buff.readBytes(16);

        //数字采集参数
        DAP_Samplefreq = buff.readFloat();
        DAP_Channelgain =buff.readFloat();
        DAP_Channeldelay =buff.readFloat();
        DAP_Samplepoints =buff.readFloat();
        buff.readBytes(16);

        //布站观测参数
        if ((int)buff.readFloat() == 1) { CSO_Targettype = "塔"; }
        CSO_Obsernearestdistance = buff.readFloat();
        CSO_Obsermaximumdistance = buff.readFloat();
        int intNoisereductionindex = (int)buff.readFloat();
        if (intNoisereductionindex == 1) { CSO_Noisereductionindex = "弱"; }
        else if (intNoisereductionindex == 2) { CSO_Noisereductionindex = "中"; }
        else if (intNoisereductionindex == 3) { CSO_Noisereductionindex = "强"; }
        CSO_Pointinterval = buff.readFloat();
        CSO_Targetverticaldistance = buff.readFloat();
        int intEnvironmentalcorrection = (int)buff.readFloat();
        if (intEnvironmentalcorrection == 0) { CSO_Environmentalcorrection = "无校正"; }
        else  if (intEnvironmentalcorrection == 1) { CSO_Environmentalcorrection = "GCP校正"; }
        else if (intEnvironmentalcorrection == 2) { CSO_Environmentalcorrection = "大气参数校正"; }
        else if (intEnvironmentalcorrection == 3) { CSO_Environmentalcorrection = "自适应补偿"; }
        buff.readBytes(4);

        //GCP参数
        GCP_Num = buff.readFloat();
        buff.readFloat();
        GCP_Distance = new float[15];
        GCP_Visualangle = new float[15];
        for (int i = 0; i < 15; i++)
        {
            GCP_Visualangle[i] = buff.readFloat();
            GCP_Distance[i] = buff.readFloat();
        }

        //观测目标参数
        OOP_Num = buff.readFloat();
        buff.readFloat();
        OOP_Visualangle = new float[99];
        OOP_Distance = new float[99];
        for (int j = 0; j< 99; j++)
        {
            OOP_Visualangle[j] = buff.readFloat();
            OOP_Distance[j] = buff.readFloat();
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