package com.jcj.sparrow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author：江成军
 * @Description: 角色表实体
 * @Date:Create in 2018/5/16 10:07
 */
@Entity
@Table(name="role")
public class Role
{
    @Id
    @Column
    private int autoid;

    @Column(length=30)
    private  String rolename;

    public int getAutoid()
    {
        return autoid;
    }

    public void setAutoid(int autoid)
    {
        this.autoid = autoid;
    }

    public String getRolename()
    {
        return rolename;
    }

    public void setRolename(String rolename)
    {
        this.rolename = rolename;
    }
}
