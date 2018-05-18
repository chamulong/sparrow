package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.SysUser;
import com.jcj.sparrow.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author：江成军
 * @Description:Spring Security控制器
 * @Date:Create in 2018/5/17 9:44
 */
@Controller
public class HomeController
{
    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
}
