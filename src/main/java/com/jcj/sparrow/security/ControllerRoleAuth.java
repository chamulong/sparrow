package com.jcj.sparrow.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private  ServiceSysAuth serviceSysAuth;

    //查询全部的角色(填充添加用户中的下拉列表)
    @RequestMapping("/roles")
    @ResponseBody
    public List<SysRole> findRoles()
    {
        List<SysRole> list=serviceSysRole.findRolesNoAdmin();
        return list;
    }


    //查询全部的角色(跳转到模板)
    @RequestMapping("/listrole")
    public String findAllRole(Model model)
    {
        List<SysRole> list=serviceSysRole.findRolesNoAdmin();
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

    //获取一级目录（主功能模块）
    @RequestMapping("/findMainAuth")
    @ResponseBody
    public List<SysAuth> findMainAuth()
    {
        return serviceSysAuth.findMainAuth();
    }

    //保存一级目录
    @PostMapping("/saveMainAuth")
    @ResponseBody
    public String saveMainAuth(SysAuth sysAuth)
    {
        sysAuth.setPid(0);
        int newId=serviceSysAuth.findMaxId(0);
        sysAuth.setId(newId);
        sysAuth.setTreename(sysAuth.getName());
        serviceSysAuth.save(sysAuth);

        return "OK";
    }

    //获取全部的权限明细，用于分组树形展示和编辑
    @RequestMapping("/listAuth")
    @ResponseBody
    public Map<String,List<ServiceSysAuth.ztree>> listAuth(@RequestParam String roleuuid)
    {
        return serviceSysAuth.findAllModule(roleuuid);
    }

    //根据name删除指定的节点及子节点
    @PostMapping("/deleteByName")
    @ResponseBody
    public String deleteByName(@RequestParam String name)
    {
        serviceSysAuth.deleteByName(name);
        return "OK";
    }

    //根据节点的id删除节点及子节点
    @PostMapping("/deleteByChild")
    @ResponseBody
    public String deleteByChild(@RequestParam int id)
    {
        serviceSysAuth.deleteByChild(id);
        return "OK";
    }

    //保存子节点（需要判断是否有重复，如果没有重复,保存信息）
    @PostMapping("/saveChildAuth")
    @ResponseBody
    public String saveChildAuth(@RequestParam int id,String name)
    {
        return serviceSysAuth.saveChildAuth(id,name);
    }

    //保存角色对应的权限信息,,其中‘authinfo’是以$分割的节点id字符串
    @PostMapping("/editRole")
    @ResponseBody
    public String editRole(@RequestParam String uuid,String authinfo)
    {
        serviceSysRole.editRole(uuid,authinfo);
        return "OK";
    }
}
