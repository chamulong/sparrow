package com.jcj.sparrow.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @Author：江成军
 * @Description:权限明细访问接口
 * @Date:Create in 2018/5/29 14:32
 */
public interface RepoSysAuth extends JpaRepository<SysAuth,Long>
{
    @Query(value = "select * from sysauth where pid=0",nativeQuery = true)
    List<SysAuth> findMainAuth();

    //根据父节点的id，获取其直接子节点的最大id
    @Query(value = "select max(id) from sysauth where pid=?1",nativeQuery = true)
    int findMaxId(int pid);

    //根据父节点的name，获取其全部的子节点
    @Query(value = "select * from sysauth where name LIKE ?1",nativeQuery = true)
    List<SysAuth> findChildAuth(String name);
}
