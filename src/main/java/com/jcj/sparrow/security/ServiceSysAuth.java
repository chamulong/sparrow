package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：江成军
 * @Description:权限明细业务层
 * @Date:Create in 2018/6/8 15:41
 */
@Service
public class ServiceSysAuth
{
    @Autowired
    private RepoSysAuth repoSysAuth;

    public List<SysAuth> findMainAuth(){return repoSysAuth.findMainAuth();}

    public void save(SysAuth sysAuth){repoSysAuth.save(sysAuth);}

    //根据父节点的id，获取其直接子节点的最大id
    public int findMaxId(int pid){return repoSysAuth.findMaxId(pid);}

    //查询所有权限明细(为多个权限树提供数据)
    public List<SysAuth> findAll(){return repoSysAuth.findAll();}



}
