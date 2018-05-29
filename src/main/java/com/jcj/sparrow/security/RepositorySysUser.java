package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author：江成军
 * @Description:账号数据访问接口
 * @Date:Create in 2018/5/29 15:27
 */
public interface RepositorySysUser extends JpaRepository<SysUser,Long>
{
    //使用SpringDataJPA方法定义查询
    SysUser findByUsername(String username);
}
