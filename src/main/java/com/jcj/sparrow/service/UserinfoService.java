package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.repository.UserinfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public void save(UserInfo userInfo)
    {
        userinfoRepo.save(userInfo);
    }

    @Transactional
    public void deleteByUuid(String uuid){
        userinfoRepo.deleteByUuid(uuid);}

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

}
