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
    private RepoSysAuth repoSysAuth;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        //首先根据账号名称、邮箱、手机号在后台用户中进行查找，三者中有其一，则进入下面的权限验证流程
        boolean blExist=false;
        UserInfo userInfo=userinfoRepo.findByUsernameOrEmailOrMobile(username,username,username);
        if(null==userInfo){throw new UsernameNotFoundException("用户不存在");}

        SysUser sysUser= repoSysUser.findByUsername(userInfo.getUsername());
        if(sysUser==null){throw new UsernameNotFoundException("用户名/密码错误");}

        //根据登录的用户，创建session,方便其他应用
        session.setAttribute("userinfo",userInfo);

        //获取用户全部的权限
        List<SysAuth> sysAuths=new ArrayList<SysAuth>();

        //判断用户对应的角色是否是‘超级管理员’，如果是直接获取全部的权限
        List<SysRole> list=sysUser.getSysRoles();
        if(list.get(0).getName().equals("超级管理员"))
        {
            List<SysAuth> listAuth=repoSysAuth.findAll();
            for (SysAuth sysAuth:listAuth)
            {
                sysAuths.add(sysAuth);
            }
        }
        else
        {
            for (SysRole sysRole:sysUser.getSysRoles())
            {
                for (SysAuth sysAuth:sysRole.getSysAuths())
                {
                    sysAuths.add(sysAuth);
                }
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
