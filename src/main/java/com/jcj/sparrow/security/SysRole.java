package com.jcj.sparrow.security;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @Author：江成军
 * @Description:角色实体
 * @Date:Create in 2018/5/29 11:04
 */
@Entity
@Table(name="sysrole")
public class SysRole
{
    //借用hibernate的方法自动生成uuid
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length=30)
    private  String name;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysAuth> sysAuths;

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

    public List<SysAuth> getSysAuths()
    {
        return sysAuths;
    }

    public void setSysAuths(List<SysAuth> sysAuths)
    {
        this.sysAuths = sysAuths;
    }
}
