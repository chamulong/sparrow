package com.jcj.sparrow.controller;

import com.jcj.sparrow.domain.Department;
import com.jcj.sparrow.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "department/list";

    }

}
