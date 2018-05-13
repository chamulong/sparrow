package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @Author:江成军
 * @Description:员工业务层
 * @Date:Created on 2018/4/21 17:27
 */
@Service
public class EmployeeService
{
    @Autowired
    private  EmployeeRepository employeeRepository;

    public void save(Employee employee)
    {
        employeeRepository.save(employee);
    }

    public List<Employee> findAll()
    {
        return employeeRepository.findAll();
    }

    public Page<Employee> findAllByPage(Pageable pageable)
    {
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> findByJPQL(String name)
    {
        return employeeRepository.findByJPQL(name);
    }

    public Page<Employee> queryData(String name,Pageable pageable)
    {
        return employeeRepository.findByJPQLAndPage(name,pageable);
    }

    public Page<Employee>  queryDynamic(Map<String,Object> reqMap, Pageable pageable)
    {
        Specification querySpecifi=new Specification<Employee>()
        {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
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

        return this.employeeRepository.findAll(querySpecifi,pageable);

    }

}

