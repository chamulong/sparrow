package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author:江成军
 * @Description:控制类，员工
 * @Date:Created on 2018/4/21 10:45
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    @ResponseBody
    public List<Employee> getAll()
    {
      return employeeService.findAll();
    }
}
