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
}
