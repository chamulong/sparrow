package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.Userinfo;
import com.jcj.sparrow.repository.UserinfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：江成军
 * @Description:Spring Security控制器
 * @Date:Create in 2018/5/17 9:44
 */
@Controller
public class HomeController
{
    @RequestMapping("/")
    public String index(Model model)
    {
        //如果登陆成功，自动跳转到首页
        return "index";
    }
}
