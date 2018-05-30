package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author：江成军
 * @Description:
 * @Date:Create in 2018/5/24 10:36
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource
{
    @Autowired
    private RepoSysAuth repoSysAuth;

    //key：资源的url，value：ConfigAttribute的集合
    private HashMap<String, Collection<ConfigAttribute>> map = null;

    //构造函数，初始化加载全部的权限
    public MyInvocationSecurityMetadataSourceService(){}

    public void loadResourceDefine()
    {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        List<SysAuth> sysAuths=new ArrayList<SysAuth>();
        sysAuths= repoSysAuth.findAll();

        for (SysAuth sysAuth:sysAuths)
        {
            array = new ArrayList();
            ConfigAttribute cfg=new SecurityConfig(sysAuth.getName());
            array.add(cfg);
            map.put(sysAuth.getUrl(),array);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
    {
        if(map == null) this.loadResourceDefine();

        //object中包含用户请求的request信息
        HttpServletRequest request =((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); )
        {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request))
            {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return true;
    }
}