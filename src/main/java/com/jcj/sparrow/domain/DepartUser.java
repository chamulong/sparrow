package com.jcj.sparrow.domain;

/**
 * 为部门和用户信息表服务的JavaBean
 */
public class DepartUser
{
    private String username;
    private String realname;
    private String homeaddress;
    private String email;
    private String depname;
    private String depbrief;

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

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDepbrief() {
        return depbrief;
    }

    public void setDepbrief(String depbrief) {
        this.depbrief = depbrief;
    }
}
