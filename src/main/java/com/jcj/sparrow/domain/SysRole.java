package com.jcj.sparrow.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @Author：江成军
 * @Description: 角色表实体
 * @Date:Create in 2018/5/16 10:07
 */
@Entity
@Table(name="sys_role")
public class SysRole
{
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(length=30)
    private  String name;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Auth> auths;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Auth> getAuths()
    {
        return auths;
    }

    public void setAuths(List<Auth> auths)
    {
        this.auths = auths;
    }
}
