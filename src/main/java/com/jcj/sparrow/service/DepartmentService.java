package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.Department;
import com.jcj.sparrow.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private DepartmentRepo departmentRepo;

    public List<Department>  findAll()
    {
        return departmentRepo.findAll();
    }

    public Page<Department> findAllPage(Pageable pageable){return departmentRepo.findAll(pageable);}
}
