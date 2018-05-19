package com.jcj.sparrow.utils;

import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author：江成军
 * @Description:系统账号业务层
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
        if(user==null)
        {
            throw new UsernameNotFoundException("用户名不存在");
        }

        return user;
    }
}
