package com.jcj.sparrow.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;

/**
 * @Author: 江成军
 * @Date: 2019/01/30 10:51
 * @Description: 测量信息实体，对应MongoDB
 */
@Document(collection = "measuredata")
public class MeasureData
{
    @Id
    private ObjectId id;
    private String poiname;
    private String datatype;
    private int num;
    private float unitprice;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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