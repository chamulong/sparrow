package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：江成军
 * @Description:角色业务层接口
 * @Date:Create in 2018/6/7 9:38
 */
@Service
public class ServiceSysRole
{
    @Autowired
    private RepoSysRole repoSysRole;

    @Autowired
    private RepoSysAuth repoSysAuth;

    //根据UUID，查找角色信息
    public SysRole findByUuid(String uuid)
    {
        return repoSysRole.findByUuid(uuid);
    }

    //查询所有的角色，‘超级管理员除外’
    public List<SysRole> findRolesNoAdmin()
    {
        return repoSysRole.findRolesNoAdmin();
    }

    @Transactional
    public void deleteByUuid(String uuid)
    {
        repoSysRole.deleteByUuid(uuid);
        repoSysRole.deleteMaptabByUuid(uuid);
    }




    //保存用户信息（包括对应的）
    public void save(SysRole sysRole){repoSysRole.save(sysRole);}

    //给选定的角色赋予权限,其中‘authsinfo’是以$分割的节点id字符串
    @Transactional
    public void editRole(String uuid,String authsinfo)
    {
        //得到角色信息
        SysRole sysRole=repoSysRole.findByUuid(uuid);

        //根据勾选的权限节点id，得到对应的权限对象，并给角色中的权限集合属性赋值
        List<SysAuth> list=new ArrayList<SysAuth>();
        String[] arrAuthid=authsinfo.split("\\$");
        for(int i=0,num=arrAuthid.length;i<num;i++)
        {
           SysAuth sysAuth=repoSysAuth.findById(Integer.parseInt(arrAuthid[i]));
            list.add(sysAuth);
        }
        sysRole.setSysAuths(list);

        //保存或者更新角色信息
        repoSysRole.save(sysRole);
    }
}
