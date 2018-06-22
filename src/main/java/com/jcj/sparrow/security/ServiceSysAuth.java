package com.jcj.sparrow.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public int findMaxId(int pid)
    {
        //判断是否有子节点


        return repoSysAuth.findMaxId(pid);
    }

    //根据name删除指定的节点及子节点
    @Transactional
    public void deleteByName(String name){repoSysAuth.deleteByName(name+"%");}

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

    //首先根据节点id查询到对应的节点信息，
    //再根据该节点信息和新的节点名称进行名称组合，以该组合名称查询对应的节点信息是否存在，
    //如果新节点信息不存在，则保存该新节点
    public String saveChildAuth(int id,String childname)
    {
        SysAuth sysAuth_Parent=repoSysAuth.findById(id);
        String strChildName=sysAuth_Parent.getName()+"_"+childname;
        SysAuth sysAuth_Child=repoSysAuth.findByName(strChildName);
        if(sysAuth_Child!=null)//子节点已经存在
        {
            JSONObject result = new JSONObject();
            result.put("msg","exist");
            return result.toJSONString();
        }
        else//节点未存在
        {
            SysAuth newAuth=new SysAuth();
            newAuth.setName(strChildName);
            newAuth.setPid(id);

            int newid=findMaxId(id);
            System.out.println("newid:"+newid);

            newAuth.setId(findMaxId(id));
            newAuth.setTreename(childname);
            repoSysAuth.save(newAuth);

            JSONObject result = new JSONObject();
            result.put("msg","ok");
            return result.toJSONString();
        }

    }





    //树形节点类
    class ztree{
        public int id;
        public int pId;
        public String name;
        public boolean open;
    }



}
