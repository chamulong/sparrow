package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：江成军
 * @Description:系统账号业务层
 * @Date:Create in 2018/5/29 14:44
 */
@Service
public class ServiceCustomUser  implements UserDetailsService
{
    @Autowired
    private RepositorySysUser repositorySysUser;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        SysUser sysUser=repositorySysUser.findByUsername(username);
        if(sysUser==null){throw new UsernameNotFoundException("用户名不存在");}

        //获取用户全部的权限
        List<SysAuth> sysAuths=new ArrayList<SysAuth>();
        for (SysRole sysRole:sysUser.getSysRoles())
        {
            for (SysAuth sysAuth:sysRole.getSysAuths())
            {
                sysAuths.add(sysAuth);
            }
        }

        //将权限信息添加到GrantedAuthority对象中，在后面进行权限验证时会使用GrantedAuthority对象
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (SysAuth sysAuth:sysAuths)
        {
            if(sysAuth!=null&&sysAuth.getName()!=null)
            {
                GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(sysAuth.getName());
                grantedAuthorities.add(grantedAuthority);
            }
        }

        String pwd = sysUser.getPassword();

        return new User(username,pwd,grantedAuthorities);
    }
}
