package com.jcj.sparrow.service;

import com.jcj.sparrow.domain.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:江成军
 * @Description:邮件发送业务层
 * @Date:Created on 2018/4/21 17:27
 */
@Service
public class MailInfoService
{
    @Autowired
    private  MailInfoService mailInfoService;

    public void save(MailInfo mailInfo)
    {
        mailInfoService.save(mailInfo);
    }
}
