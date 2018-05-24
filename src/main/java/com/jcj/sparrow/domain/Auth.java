package com.jcj.sparrow.domain;

import javax.persistence.*;

/**
 * @Author：江成军
 * @Description:权限明细表
 * @Date:Create in 2018/5/24 8:31
 */
@Entity
@Table(name = "auth")
public class Auth
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键

    private String name;//权限名称

    private String descritpion;//权限描述

    private String url;//授权链接

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
}
