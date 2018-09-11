package com.jcj.sparrow.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 系统操作日志实体
 */
@Entity
@Table(name="systemlog")
public class Systemlog
{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 20)
    private String username;//账号

    @Column(length = 20)
    private String realname;//真实姓名

    @Column(length = 100)
    private String depname;//所属部门

    @Column(length = 19)
    private String operatetime;//操作时间

    @Column(length = 2000)
    private String operetedesc;//操作内容

    @Column(length = 60)
    private String operetetype;//操作简述

    @Column(length = 30)
    private String osname;//操作系统名称

    @Column(length = 30)
    private String browser;//浏览器名称

    @Column(length = 20)
    private String ip;//用户IP

    @Column(length = 30)
    private String methodname;//调用的方法

    @Column(length = 120)
    private String classname;//类模块

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(String operatetime) {
        this.operatetime = operatetime;
    }

    public String getOperetedesc() {
        return operetedesc;
    }

    public void setOperetedesc(String operetedesc) {
        this.operetedesc = operetedesc;
    }

    public String getOperetetype() {
        return operetetype;
    }

    public void setOperetetype(String operetetype) {
        this.operetetype = operetetype;
    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
