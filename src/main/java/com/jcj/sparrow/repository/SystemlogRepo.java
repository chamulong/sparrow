package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.Systemlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 系统操作日志数据访问接口
 */
public interface SystemlogRepo extends JpaRepository<Systemlog,Long>,JpaSpecificationExecutor
{
}
