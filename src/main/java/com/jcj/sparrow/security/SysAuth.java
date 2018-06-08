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

    @Column(length = 200)
    private String name;//权限名称（唯一），如‘系统管理’,‘系统管理_部门成员_增加’

    @Column(length = 20)
    private String treename;//树形节点名称，如‘增加’

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

    public String getTreename()
    {
        return treename;
    }

    public void setTreename(String treename)
    {
        this.treename = treename;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public int getPid(){return pid;}

    public void setPid(int pid){this.pid = pid;}
}
