package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:江成军
 * @Description:员工数据访问接口
 * @Date:Created on 2018/4/21 17:19
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long>
{
}
