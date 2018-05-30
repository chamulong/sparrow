package com.jcj.sparrow.domain;

import com.jcj.sparrow.security.SysUser;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author:江成军
 * @Description:后台用户实体
 * @Date:Created on 2018/4/21 10:44
 */
@Entity
@Table(name = "userinfo")
public class UserInfo
{
    @Id
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 20)
    private String username;

    @Column(length = 20)
    private String realname;

    @Column(length = 10)
    private String sextype;

    @Column(length = 32)
    private String depuuid;

    @Column(length = 100)
    private String depname;

    @Column(length = 20)
    private String birthdate;

    @Column(length = 40)
    private String nativeplace;

    @Column(length = 100)
    private String homeaddress;

    @Column(length = 60)
    private String email;

    @Column(length = 20)
    private String position;

    @Column(length = 10)
    private String status;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public String getRealname() {return realname;}

    public void setRealname(String realname) {this.realname = realname;}

    public String getSextype()
    {
        return sextype;
    }

    public void setSextype(String sextype)
    {
        this.sextype = sextype;
    }

    public String getDepuuid()
    {
        return depuuid;
    }

    public void setDepuuid(String depuuid)
    {
        this.depuuid = depuuid;
    }

    public String getDepname()
    {
        return depname;
    }

    public void setDepname(String depname)
    {
        this.depname = depname;
    }

    public String getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(String birthdate)
    {
        this.birthdate = birthdate;
    }

    public String getNativeplace()
    {
        return nativeplace;
    }

    public void setNativeplace(String nativeplace)
    {
        this.nativeplace = nativeplace;
    }

    public String getHomeaddress()
    {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress)
    {
        this.homeaddress = homeaddress;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

}
