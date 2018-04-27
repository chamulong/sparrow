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

    public Page<Employee>  queryDynamic(Employee employee, Pageable pageable)
    {
        Specification querySpecifi=new Specification<Employee>()
        {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                if(!employee.getDepname().equals(""))//部门名称，like 模糊查询
                {
                    System.out.println("部门名称:"+employee.getDepname());
                    predicates.add(criteriaBuilder.like(root.get("depname"),"%"+employee.getDepname()+"%"));
                }
                if(!employee.getUsername().equals(""))//姓名，= 等于
                {
                    predicates.add(criteriaBuilder.equal(root.get("username"),employee.getUsername()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return this.employeeRepository.findAll(querySpecifi,pageable);

    }

}

