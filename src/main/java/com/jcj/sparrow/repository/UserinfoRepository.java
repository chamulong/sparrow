package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author：江成军
 * @Description://用户数据访问接口
 * @Date:Create in 2018/5/16 11:22
 */
public interface UserinfoRepository extends JpaRepository<Userinfo,Long>,JpaSpecificationExecutor
{
    //根据账号进行用户信息查询
    @Query(value = "select * from userinfo where account=?1",nativeQuery = true)
    Userinfo findByJPQL(String account);
}
