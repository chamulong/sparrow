package com.jcj.sparrow.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author：江成军
 * @Description:Spring Security控制器
 * @Date:Create in 2018/5/17 9:44
 */
@Controller
public class ControllerHome
{
    @RequestMapping(value = "/home")
    public String index()
    {
        return "home";
    }

    @RequestMapping(value = "/listuserinfo")
    public String listemployee()
    {
        return "/userinfo/listUserInfo";
    }

    @RequestMapping(value = "/listRoleAuth")
    public String listRoleAuth()
    {
        return "/authority/listRoleAuth";
    }


    @RequestMapping(value="/login")
    public String login(){return "login";}
}
