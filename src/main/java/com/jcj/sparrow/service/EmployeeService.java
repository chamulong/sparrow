package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

}

