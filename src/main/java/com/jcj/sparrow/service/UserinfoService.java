package com.jcj.sparrow.service;

import com.alibaba.fastjson.JSON;
import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.repository.UserinfoRepo;
import com.jcj.sparrow.security.RepoSysUser;
import com.jcj.sparrow.security.ServiceSysRole;
import com.jcj.sparrow.security.SysRole;
import com.jcj.sparrow.security.SysUser;
import com.jcj.sparrow.systemaop.SystemAnnotationLog;
import com.jcj.sparrow.utils.FilterPureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @Author:江成军
 * @Description:员工业务层
 * @Date:Created on 2018/4/21 17:27
 */
@Service
public class UserinfoService
{
    @Autowired
    private UserinfoRepo userinfoRepo;

    @Autowired
    private RepoSysUser repoSysUser;

    @Autowired
    private ServiceSysRole serviceSysRole;

    //新添加用户
    @SystemAnnotationLog(actiondesc = "新添加用户")
    public void save(UserInfo userInfo)
    {
        userInfo.setStatus("启用");//用户的默认状态是启用

        //密码进行BCrypt强哈希加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userInfo.getPassword());

        //创建账户对象
        SysUser sysUser=new SysUser();
        sysUser.setUsername(userInfo.getUsername());
        sysUser.setPassword(hashedPassword);
        sysUser.setProjectname("sparrow");
        //根据roleuuid，得到角色信息
        SysRole sysRole=serviceSysRole.findByUuid(userInfo.getRoleuuid());
        List<SysRole> list=new ArrayList<SysRole>();
        list.add(sysRole);
        sysUser.setSysRoles(list);

        userInfo.setSysUser(sysUser);

        userInfo.setPassword(hashedPassword);
        userinfoRepo.save(userInfo);
    }

    //修改用户
    @SystemAnnotationLog(actiondesc = "修改用户信息")
    @Transactional
    public String modify(UserInfo userInfo)
    {
        //先删除用户信息，再添加用户信息（这样可以通过实体中配置的映射关系，简洁利索的处理角色权限关系）
        UserInfo deluserInfo=userinfoRepo.findByUuid(userInfo.getUuid());
        userinfoRepo.delete(deluserInfo);

        //*****添加用户信息及一系列的账号信息
        userInfo.setStatus("启用");//用户的默认状态是启用

        //密码进行BCrypt强哈希加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userInfo.getPassword());

        //创建账户对象
        SysUser sysUser=new SysUser();
        sysUser.setUsername(userInfo.getUsername());
        sysUser.setPassword(hashedPassword);
        sysUser.setProjectname("sparrow");
        //根据roleuuid，得到角色信息
        SysRole sysRole=serviceSysRole.findByUuid(userInfo.getRoleuuid());
        List<SysRole> list=new ArrayList<SysRole>();
        list.add(sysRole);
        sysUser.setSysRoles(list);

        userInfo.setSysUser(sysUser);

        userInfo.setPassword(hashedPassword);
        userinfoRepo.save(userInfo);

        Map<String,Object> mapBefore = FilterPureEntity.getKeyAndValue(deluserInfo);//修改前的实体信息
        Map<String,Object> mapAfter = FilterPureEntity.getKeyAndValue(userInfo);//修改后的实体信息
        Map<String,String> mapNew=new HashMap<String,String>();
        for (String key:mapBefore.keySet()) {
            String strBefore="";
            if(mapBefore.get(key)!=null){strBefore=mapBefore.get(key).toString();}
            String strAfter="";
            if(mapAfter.get(key)!=null){strAfter=mapAfter.get(key).toString();}
            if(!strBefore.equals(strAfter))
            {
                System.out.println(key);
                if(!(key.equals("password")||key.equals("roleuuid")))
                {
                    mapNew.put(key,strBefore+" => "+strAfter);
                }
            }
        }
        return JSON.toJSONString(mapNew);

    }

    @Transactional
    public void deleteByUuid(String uuid){userinfoRepo.deleteByUuid(uuid);}

    public List<UserInfo> findAll()
    {
        return userinfoRepo.findAll();
    }

    public Page<UserInfo> findAllByPage(Pageable pageable)
    {
        return userinfoRepo.findAll(pageable);
    }

    public List<UserInfo> findByJPQL(String name)
    {
        return userinfoRepo.findByJPQL(name);
    }

    public Page<UserInfo> queryData(String name, Pageable pageable)
    {
        return userinfoRepo.findByJPQLAndPage(name,pageable);
    }

    public Page<UserInfo>  queryDynamic(Map<String,Object> reqMap, Pageable pageable)
    {
        Specification querySpecifi=new Specification<UserInfo>()
        {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                if(!reqMap.get("username").toString().equals(""))//员工名称，like 模糊查询
                {
                    predicates.add(criteriaBuilder.like(root.get("username"),"%"+reqMap.get("username").toString()+"%"));
                }
                if(!reqMap.get("depname").toString().equals("请选择"))//部门名称，精确查询
                {
                    predicates.add(criteriaBuilder.equal(root.get("depname"),reqMap.get("depname").toString()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return this.userinfoRepo.findAll(querySpecifi,pageable);

    }

    //检查用户名的唯一性,如果用户已经存在，返回false，否则返回true
    public boolean validateUsername(String username)
    {
        int intCount=userinfoRepo.validateUsername(username);
        if(intCount==0){return true;}
        else{return false;}
    }

    //检查手机号码的唯一性,如果手机号码已经存在，返回false，否则返回true
    public boolean validateMobile(String mobile)
    {
        int intCount=userinfoRepo.validateMobile(mobile);
        if(intCount==0){return true;}
        else{return false;}
    }

    //检查邮箱号的唯一性,如果邮箱号已经存在，返回false，否则返回true
    public boolean validateEmail(String email)
    {
        int intCount=userinfoRepo.validateEmail(email);
        if(intCount==0){return true;}
        else{return false;}
    }

    //用于修改，检查手机号码的唯一性,如果手机号码已经存在，返回false，否则返回true
    public boolean validateMobileModify(String mobile,String uuid)
    {
        int intCount=userinfoRepo.validateMobileModify(mobile,uuid);
        if(intCount==0){return true;}
        else{return false;}
    }

    //用于修改，检查邮箱号的唯一性,如果邮箱号已经存在，返回false，否则返回true
    public boolean validateEmailModify(String email,String uuid)
    {
        int intCount=userinfoRepo.validateEmailModify(email,uuid);
        if(intCount==0){return true;}
        else{return false;}
    }

    //根据用户uuid得到用户实体，根据实体进行用户数据库记录的删除
    //（不直接根据uuid进行删除，是想通过用户实体配置的关联关系，级联删除权限中的账号信息）
    @SystemAnnotationLog(actiondesc = "删除用户")
    @Transactional
    public String delete(String uuid)
    {
        UserInfo userInfo=userinfoRepo.findByUuid(uuid);
        userinfoRepo.delete(userInfo);

        Map map = FilterPureEntity.getKeyAndValue(userInfo);
        return JSON.toJSONString(map);
    }

    //根据uuid查询用户信息
    public UserInfo findByUuid(String uuid)
    {
        return userinfoRepo.findByUuid(uuid);
    }

}

