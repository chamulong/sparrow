package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author：江成军
 * @Description:角色、权限的各项管理
 * @Date:Create in 2018/6/7 9:30
 */
@Controller
@RequestMapping("/roleauth")
public class ControllerRoleAuth
{
    @Autowired
    private ServiceSysRole serviceSysRole;

    //查询全部的角色
    @RequestMapping("/listrole")
    public String findAllRole(Model model)
    {
        List<SysRole> list=serviceSysRole.findAll();
        model.addAttribute("sysroles",list);
        return "/authority/listRoleAuth";
    }
}
