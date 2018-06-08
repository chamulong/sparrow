package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author：江成军
 * @Description:权限明细访问接口
 * @Date:Create in 2018/5/29 14:32
 */
public interface RepoSysAuth extends JpaRepository<SysAuth,Long>
{
    @Query(value = "select * from sysauth where pid=0",nativeQuery = true)
    List<SysAuth> findMainAuth();
}
