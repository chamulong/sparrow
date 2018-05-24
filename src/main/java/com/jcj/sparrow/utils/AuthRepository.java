package com.jcj.sparrow.utils;

import com.jcj.sparrow.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author：江成军
 * @Description:权限明细表访问接口
 * @Date:Create in 2018/5/24 10:58
 */
public interface AuthRepository extends JpaRepository<Auth,Long>
{
}
