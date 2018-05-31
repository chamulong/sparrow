package com.jcj.sparrow.security;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Author：江成军
 * @Description:权限明细实体
 * @Date:Create in 2018/5/29 11:04
 */
@Entity
@Table(name = "sysauth")
public class SysAuth
{
    //借用hibernate的方法自动生成uuid
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    private String name;//权限名称

    private String descritpion;//权限描述

    private String url;//授权链接

    private int id;//本身id

    private int pid;//父id

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescritpion()
    {
        return descritpion;
    }

    public void setDescritpion(String descritpion)
    {
        this.descritpion = descritpion;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public int getPid(){return pid;}

    public void setPid(int pid){this.pid = pid;}
}
