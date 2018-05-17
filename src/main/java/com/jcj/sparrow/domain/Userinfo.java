package com.jcj.sparrow.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：江成军
 * @Description:用户信息表（账号表）
 * @Date:Create in 2018/5/16 9:53
 */
@Entity
@Table(name="userinfo")
public class Userinfo implements Serializable,UserDetails
{
    @Id
    @Column
    private int autoid;

    @Column(length=50)
    private  String account;

    @Column(length=255)
    private  String pwd;

    public int getAutoid()
    {
        return autoid;
    }

    public void setAutoid(int autoid)
    {
        this.autoid = autoid;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @ManyToMany
    @JoinTable(
            name="userrole",
            joinColumns = {
                    @JoinColumn(name="userid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="roleid")
            }

    )
    private List<Role> roles;

    public List<Role> getRoles()
    {
        return roles;
    }


    /**
     * 通过用户表实体来完成用户认证功能，需要实现getAuthorities方法内容，将定义的角色列表添加到授权的列表内
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> auths=new ArrayList<>();
        List<Role> roles=getRoles();
        for(Role role:roles)
        {
            auths.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        return auths;
    }

    @Override
    public String getPassword()
    {
        return this.pwd;
    }

    @Override
    public String getUsername()
    {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }


}
