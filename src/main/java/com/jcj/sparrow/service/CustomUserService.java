package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.SysRole;
import com.jcj.sparrow.domain.SysUser;
import com.jcj.sparrow.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：江成军
 * @Description:系统账号业务层
 * @Date:Create in 2018/5/16 16:31
 */
public class CustomUserService implements UserDetailsService
{
    @Autowired
    private SysUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
    {
        //SysUser user=new SysUser();
        //user.setUsername("111");
        //user.setPassword("111");
        SysUser user=userRepository.findByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("用户名不存在");
        }

        return user;
    }
}
