package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.Department;
import com.jcj.sparrow.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：江成军
 * @Description:部门业务层
 * @Date:Create in 2018/5/3 10:19
 */
@Service
public class DepartmentService
{
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department>  findAll()
    {
        return departmentRepository.findAll();
    }
}
