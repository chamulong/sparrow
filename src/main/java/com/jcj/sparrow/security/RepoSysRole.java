package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author：江成军
 * @Description:角色访问接口
 * @Date:Create in 2018/6/7 9:32
 */
public interface RepoSysRole  extends JpaRepository<SysRole,Long>
{

}
