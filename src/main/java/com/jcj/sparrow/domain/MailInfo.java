package com.jcj.sparrow.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 记录要发送的邮件信息的实体
 */
@Entity
@Table(name = "mailinfo")
public class MailInfo
{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    //发送者邮箱
    @Column(length = 100)
    private String frommail;

    //接收者邮箱
    @Column(length = 100)
    private String tomail;

    //发送的标题
    @Column(length = 200)
    private String subject;

    //发送的内容
    @Column(length = 1000)
    private String content;

    //发送的时间
    @Column(length = 19)
    private String sendtime;

    //发送状态
    @Column(length = 10)
    private String sendstatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFrommail() {
        return frommail;
    }

    public void setFrommail(String frommail) {
        this.frommail = frommail;
    }

    public String getTomail() {
        return tomail;
    }

    public void setTomail(String tomail) {
        this.tomail = tomail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSendstatus() {
        return sendstatus;
    }

    public void setSendstatus(String sendstatus) {
        this.sendstatus = sendstatus;
    }
}
