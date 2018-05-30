package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author：江成军
 * @Description:部门数据访问接口
 * @Date:Create in 2018/5/3 10:16
 */
public interface DepartmentRepo extends JpaRepository<Department,Long>,JpaSpecificationExecutor
{
}
