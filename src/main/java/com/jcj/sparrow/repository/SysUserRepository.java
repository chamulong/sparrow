package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author：江成军
 * @Description://用户数据访问接口
 * @Date:Create in 2018/5/16 11:22
 */
public interface SysUserRepository extends JpaRepository<SysUser,Long>
{
    //使用SpringDataJPA方法定义查询
    public SysUser findByUsername(String username);
}
