package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<SysRole> findAll(){return repoSysRole.findAll();}
}
