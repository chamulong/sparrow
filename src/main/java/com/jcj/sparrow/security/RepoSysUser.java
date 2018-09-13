package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author：江成军
 * @Description:账号数据访问接口
 * @Date:Create in 2018/5/29 15:27
 */
public interface RepoSysUser extends JpaRepository<SysUser,Long>
{
    //使用SpringDataJPA方法定义查询账户信息（账号名）
    SysUser findByUsername(String username);

    //使用SpringDataJPA方法定义删除账户信息（账号名）
    SysUser deleteByUsername(String username);
}
