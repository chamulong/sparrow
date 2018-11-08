package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    //根据uuid查找角色角色信息
    SysRole findByUuid(String uuid);

    //删除角色权限关联表中角色对应的记录
    @Modifying
    @Query(value = "delete from sysrole_sys_auths where sys_role_uuid=?1",nativeQuery = true)
    void deleteMaptabByUuid(String uuid);

    //查询所有的角色
    @Query(value = "select * from sysrole",nativeQuery = true)
    List<SysRole> findRoles();

}
