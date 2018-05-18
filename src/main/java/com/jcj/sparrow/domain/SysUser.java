package com.jcj.sparrow.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：江成军
 * @Description:用户信息表（账号表）
 * @Date:Create in 2018/5/16 9:53
 */
@Entity
@Table(name="sys_user")
public class SysUser implements UserDetails
{
    @Id
    @Column
    private int id;

    @Column(length=50)
    private  String username;

    @Column(length=255)
    private  String password;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysRole> roles;


    /**
     * 通过用户表实体来完成用户认证功能，需要实现getAuthorities方法内容，将定义的角色列表添加到授权的列表内
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
        List<SysRole> roles=getRoles();
        for(SysRole role:roles)
        {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }

        return auths;
    }



    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }
}
