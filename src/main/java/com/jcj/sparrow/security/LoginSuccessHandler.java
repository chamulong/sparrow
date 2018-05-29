package com.jcj.sparrow.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：江成军
 * @Description:登录成功后可使用loginSuccessHandler()存储用户信息
 * @Date:Create in 2018/5/29 15:06
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,ServletException
    {
        //获得授权后可得到用户信息
        SysUser sysUser = (SysUser)authentication.getPrincipal();

        //输出登录提示信息
        System.out.println("登录账号是: " + sysUser.getUsername());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
