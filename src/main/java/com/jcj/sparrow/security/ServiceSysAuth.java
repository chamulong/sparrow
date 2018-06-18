package com.jcj.sparrow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,List<ztree>> findAllModule()
    {
        Map<String,List<ztree>> map=new HashMap<String,List<ztree>>();

        List<SysAuth> sysAuths=findMainAuth();
        for (SysAuth sysAuth:sysAuths)
        {
           String name=sysAuth.getName();
           List<SysAuth> list=repoSysAuth.findChildAuth(name+"%");
           List<ztree> listztree=new ArrayList<ztree>();
            for (SysAuth s:list)
            {
                ztree z=new ztree();
                z.id=s.getId();
                z.pId=s.getPid();
                z.name=s.getTreename();
                z.open=true;
                listztree.add(z);
            }

           map.put(name,listztree);
        }
        return map;
    }


    //树形节点类
    class ztree{
        public int id;
        public int pId;
        public String name;
        public boolean open;
    }



}
