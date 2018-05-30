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
    @Query(value = "select * from employee where username=?1",nativeQuery = true)
    List<UserInfo> findByJPQL(String name);

    @Query(value = "update * from employee where username like %?%1",nativeQuery = true)
    Page<UserInfo> findByJPQLAndPage(String name, Pageable pageable);

    @Modifying
    @Query(value = "update employee set status ='停用' where uuid=?1",nativeQuery = true)
    void deleteByUuid(String uuid);

    //使用SpringDataJPA方法定义查询
    UserInfo findByUsername(String username);
}