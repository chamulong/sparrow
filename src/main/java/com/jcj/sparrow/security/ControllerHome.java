package com.jcj.sparrow.security;

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

    @RequestMapping(value = "/listemployee")
    public String listemployee()
    {
        return "/employee/listEmployee";
    }
}
