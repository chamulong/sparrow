package com.jcj.sparrow.utils;

import com.jcj.sparrow.domain.MailInfo;
import com.jcj.sparrow.service.MailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送邮件的工具类
 */
public class MailTool
{
    @Autowired
    JavaMailSender jms;

    @Autowired
    MailInfoService mailInfoService;

    /**
     * 发送邮件，并把发送的内容记录在数据库，
     * @return 发送成功true，否则false
     */
    public String sendMail(MailInfo mailInfo)
    {
        String sendStatus="发送成功";
        try
        {
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            mainMessage.setFrom(mailInfo.getFrommail());
            mainMessage.setTo(mailInfo.getTomail());
            mainMessage.setSubject(mailInfo.getSubject());
            mainMessage.setText(mailInfo.getContent());
            jms.send(mainMessage);

        }
        catch (Exception e)
        {
            sendStatus="发送失败";
        }

        //发送记录存入数据库
        mailInfo.setSendstatus(sendStatus);
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mailInfo.setSendtime(sdf.format(nowDate));
        mailInfoService.save(mailInfo);

        return sendStatus;
    }
}
