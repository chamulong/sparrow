package com.jcj.sparrow.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;


/**
 * @Author: 江成军
 * @Date: 2019/01/30 10:51
 * @Description: 测量信息实体，对应MongoDB
 */
@Document(collection = "measuredata")
public class MeasureData
{
    private String uuid;
    private String poiname;
    private String datatype;
    private int num;
    private float unitprice;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(float unitprice) {
        this.unitprice = unitprice;
    }
}