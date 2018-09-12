package com.jcj.sparrow.security;

import com.jcj.sparrow.systemaop.SystemAnnotationLog;
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
    @SystemAnnotationLog(actiondesc = "用户登录")
    public String index()
    {
        return "home";
    }

    @RequestMapping(value = "/listRoleAuth")
    public String listRoleAuth()
    {
        return "/authority/listRoleAuth";
    }


    @RequestMapping(value="/login")
    public String login(){return "/login";}
}
