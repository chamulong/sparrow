package com.jcj.sparrow.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:江成军
 * @Description:部门表实体
 * @Date:Created on 2018/4/21 10:44
 */
@Entity
@Table(name = "department")
public class Department
{
    @Id
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 100)
    private String depname;

    @Column(length = 20)
    private String chargename;

    @Column(length = 300)
    private String depbrief;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getDepname()
    {
        return depname;
    }

    public void setDepname(String depname)
    {
        this.depname = depname;
    }

    public String getChargename()
    {
        return chargename;
    }

    public void setChargename(String chargename)
    {
        this.chargename = chargename;
    }

    public String getDepbrief()
    {
        return depbrief;
    }

    public void setDepbrief(String depbrief)
    {
        this.depbrief = depbrief;
    }
}
