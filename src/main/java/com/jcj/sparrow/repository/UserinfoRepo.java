package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author:江成军
 * @Description:员工数据访问接口
 * @Date:Created on 2018/4/21 17:19
 */
public interface UserinfoRepo extends JpaRepository<UserInfo,Long>,JpaSpecificationExecutor
{
    @Query(value = "select * from userinfo where username=?1",nativeQuery = true)
    List<UserInfo> findByJPQL(String name);

    @Query(value = "update * from userinfo where username like %?%1",nativeQuery = true)
    Page<UserInfo> findByJPQLAndPage(String name, Pageable pageable);

    @Modifying
    @Query(value = "update userinfo set status ='停用' where uuid=?1",nativeQuery = true)
    void deleteByUuid(String uuid);

    //使用SpringDataJPA方法定义查询,根据用户名，查询用户信息
    UserInfo findByUsername(String username);

    //使用SpringDataJPA方法定义查询,根据用户名/邮件/手机号，查询用户信息
    UserInfo findByUsernameOrEmailOrMobile(String username,String email,String mobile);

    //查询对应的账号名称是否存在（服务层用于唯一性验证）
    @Query(value = "select count(*) from userinfo where username=?1",nativeQuery = true)
    int validateUsername(String username);

    //查询对应的手机号码是否存在（服务层用于唯一性验证）
    @Query(value = "select count(*) from userinfo where mobile=?1",nativeQuery = true)
    int validateMobile(String mobile);

    //查询对应的邮箱是否存在（服务层用于唯一性验证）
    @Query(value = "select count(*) from userinfo where email=?1",nativeQuery = true)
    int validateEmail(String email);
}
