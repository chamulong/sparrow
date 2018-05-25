package com.jcj.sparrow.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author：江成军
 * @Description:Spring Security控制器
 * @Date:Create in 2018/5/17 9:44
 */
@Controller
public class HomeController
{
    @RequestMapping(value = "/listemployee")
    public String listemployee(){
        return "/employee/listEmployee";
    }
}
