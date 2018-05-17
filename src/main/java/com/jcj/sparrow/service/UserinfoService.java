package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.Userinfo;
import com.jcj.sparrow.repository.UserinfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：江成军
 * @Description:系统账号业务层
 * @Date:Create in 2018/5/16 16:31
 */
@Service
public class UserinfoService implements UserDetailsService
{
    @Autowired
    private UserinfoRepository userinfoRepository;

    public Userinfo findByJPQL(String account)
    {
        return userinfoRepository.findByJPQL(account);
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException
    {
        Userinfo userinfo=userinfoRepository.findByJPQL(account);
        if(userinfo==null)
        {
            throw new UsernameNotFoundException("未查询到账号:"+account+"信息！");
        }
        return userinfo;
    }
}
