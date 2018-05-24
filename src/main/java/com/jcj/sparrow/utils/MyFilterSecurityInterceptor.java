package com.jcj.sparrow.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;
import javax.servlet.*;
import java.io.IOException;

/**
 * @Author：江成军
 * @Description:访问资源拦截器
 * @Date:Create in 2018/5/24 10:27
 */
@Service
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor implements Filter
{
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManage(MyAccessDecisionManager myAccessDecisionManager)
    {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException
    {
        /*fi里面有一个被拦截的URL，里面调用MyInvocationSecurityMetadataSource的getAttributes（Object object）方法，
        来获fi对应的所有权限，再调用MyAccessDecisionManager的decide方法来校验用的权限是否足够*/

        InterceptorStatusToken token = super.beforeInvocation(fi);
        try
        {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }
        finally
        {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy()
    {

    }

    @Override
    public Class<?> getSecureObjectClass()
    {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource()
    {
        return this.securityMetadataSource;
    }
}
