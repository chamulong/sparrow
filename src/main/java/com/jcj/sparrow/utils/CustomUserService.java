package com.jcj.sparrow.utils;

import com.jcj.sparrow.domain.Auth;
import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.domain.SysRole;
import com.jcj.sparrow.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：江成军
 * @Description:系统账号业务层--用于权限控制
 * @Date:Create in 2018/5/16 16:31
 */
@Service
public class CustomUserService implements UserDetailsService
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        Employee user=employeeRepository.findByUsername(username);
        if(user==null){throw new UsernameNotFoundException("用户名不存在");}

        //获取用户全部的权限
        List<Auth> auths=new ArrayList<Auth>();
        for (SysRole role:user.getRoles())
        {
            for (Auth auth:role.getAuths())
            {
                auths.add(auth);
            }
        }

        //将权限信息添加到GrantedAuthority对象中，在后面进行权限验证时会使用GrantedAuthority对象
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Auth auth:auths)
        {
            if(auth!=null&&auth.getName()!=null)
            {
                GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(auth.getName());
                grantedAuthorities.add(grantedAuthority);
            }
        }

        String pwd = user.getPassword();

        return new User(username,pwd,grantedAuthorities);
    }
}
