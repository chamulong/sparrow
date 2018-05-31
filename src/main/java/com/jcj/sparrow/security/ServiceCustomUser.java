package com.jcj.sparrow.security;

import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.repository.UserinfoRepo;
import com.jcj.sparrow.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpSession;
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
    private RepoSysUser repoSysUser;

    @Autowired
    private UserinfoRepo userinfoRepo;

    @Autowired
    private HttpSession session;


    @Override
    public UserDetails loadUserByUsername(String username)
    {
        SysUser sysUser= repoSysUser.findByUsername(username);
        if(sysUser==null){throw new UsernameNotFoundException("用户名不存在");}

        //根据登录的用户，创建session,方便其他应用
        UserInfo userInfo=userinfoRepo.findByUsername(username);
        session.setAttribute("userinfo",userInfo);

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
