package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.Employee;
import com.jcj.sparrow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/comquery")
    @ResponseBody
    public List<Employee> myQuery(@RequestParam(defaultValue = "张三") String username)
    {
        return employeeService.findByJPQL(username);
    }

    @GetMapping("/qd")
    @ResponseBody
    public Page<Employee> getData(@RequestParam(defaultValue = "0") int page,String username,@RequestParam(defaultValue = "8") int size)
    {
        Sort sort=new Sort(Sort.Direction.DESC,"birthdate");
        return employeeService.queryData(username,new PageRequest(page,size,sort));
    }

    @GetMapping("/aa")
    @ResponseBody
    public Page<Employee> getDataD(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size,String username,String depname)
    {
        Employee employee=new Employee();
        employee.setDepname(depname);
        employee.setUsername(username);
        Sort sort=new Sort(Sort.Direction.DESC,"birthdate");
        return employeeService.queryDynamic(employee,new PageRequest(page,size,sort));
    }

    @GetMapping("/d1")
    public String showDIndex(Model model)
    {
        List<Employee> list=employeeService.findAll();
        model.addAttribute("employee",list);
        return "employee/index";
    }

    @GetMapping("/index")
    public String showIndex()
    {
        return "index";
    }
}
