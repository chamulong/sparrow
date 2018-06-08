package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //根据uuid删除指定的角色
    @PostMapping("/deleterole")
    public String deleteByUuid(@RequestParam String uuid)
    {
        serviceSysRole.deleteByUuid(uuid);
        return "/authority/listRoleAuth";
    }
    //保存角色
    @PostMapping("/save")
    @ResponseBody
    public String save(SysRole sysRole)
    {
        serviceSysRole.save(sysRole);
        return "OK";
    }
}
