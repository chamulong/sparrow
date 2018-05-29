package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author：江成军
 * @Description:权限明细访问接口
 * @Date:Create in 2018/5/29 14:32
 */
public interface RepositorySysAuth extends JpaRepository<SysAuth,Long>
{
}
