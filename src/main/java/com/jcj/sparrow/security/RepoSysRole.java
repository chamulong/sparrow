package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author：江成军
 * @Description:角色访问接口
 * @Date:Create in 2018/6/7 9:32
 */
public interface RepoSysRole  extends JpaRepository<SysRole,Long>
{
    @Modifying
    @Query(value = "delete from sysrole where uuid=?1",nativeQuery = true)
    void deleteByUuid(String uuid);
}
