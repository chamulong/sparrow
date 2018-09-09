package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.Systemlog;
import com.jcj.sparrow.repository.SystemlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 系统日志业务层
 */
@Service
public class SystemlogService
{
    @Autowired
    private SystemlogRepo systemlogRepo;

    //保存系统操作日志
    public void save(Systemlog systemlog)
    {
        systemlogRepo.save(systemlog);
    }

    //带查询条件的分页查询
    public Page<Systemlog> queryDynamic(Map<String,Object> reqMap, Pageable pageable)
    {
        Specification querySpecifi=new Specification<Systemlog>()
        {
            @Override
            public Predicate toPredicate(Root<Systemlog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                if(!reqMap.get("operetedesc").toString().equals(""))//操作内容，like 模糊查询
                {
                    predicates.add(criteriaBuilder.like(root.get("operetedesc"),"%"+reqMap.get("operetedesc").toString()+"%"));
                }
                if(!reqMap.get("realname").toString().equals("请选择"))//操作者真实姓名，精确查询
                {
                    predicates.add(criteriaBuilder.equal(root.get("realname"),reqMap.get("realname").toString()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return this.systemlogRepo.findAll(querySpecifi,pageable);

    }

}
