package com.jcj.sparrow.repository;

import com.jcj.sparrow.domain.MailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author:江成军
 * @Description:邮件发送访问接口
 * @Date:Created on 2018/8/21 17:12
 */
public interface MailInfoRepo extends JpaRepository<MailInfo,Long>,JpaSpecificationExecutor
{
}
