package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.Department;
import com.jcj.sparrow.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:江成军
 * @Description:控制类，部门相关
 * @Date:Created on 2018/4/21 10:46
 */
@Controller
@RequestMapping("/department")
public class DepartmentController
{
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public String findAll(Model model)
    {
        List<Department> list=departmentService.findAll();
        model.addAttribute("departments",list);
        return "department/listDepartment";

    }

    @GetMapping("/PageDepartments")
    public String findAll(Model model,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "depname");
        Page<Department> pageDep=departmentService.findAllPage(new PageRequest(page,size,sort));
        model.addAttribute("page",pageDep);
        return "department/listDepartment";
    }

}
