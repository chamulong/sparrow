package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/emppage")
    @ResponseBody
    public Page<Employee> getPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "2") int size)
    {
        Sort sort=new Sort(Sort.Direction.DESC,"depname");
        return employeeService.findAllByPage(new PageRequest(page,size,sort));
    }
}
