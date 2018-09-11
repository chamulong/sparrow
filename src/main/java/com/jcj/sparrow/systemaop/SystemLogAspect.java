package com.jcj.sparrow.systemaop;

import com.alibaba.fastjson.JSON;
import com.jcj.sparrow.domain.Systemlog;
import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.service.SystemlogService;
import com.jcj.sparrow.utils.FilterPureEntity;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:  定义日志切入类，拦截Service
 * @Author: 江成军
 * @CreateDate: 2018/09/11 上午09:28
 */
@Aspect
@Component
public class SystemLogAspect
{
    @Autowired
    private SystemlogService systemlogService;

    /***
     * 定义切入点拦截规则，拦截SystemAnnotationLog注解的方法
     */
    @Pointcut("@annotation(com.jcj.sparrow.systemaop.SystemAnnotationLog)")
    public void logAspect(){}

    /***
     * 获取controller的注解的操作类型
     */
    public String getControllerMethodActionType(ProceedingJoinPoint point) throws  Exception
    {
        //获取连接点目标类名
        String targetName = point.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = point.getSignature().getName() ;
        //获取连接点参数
        Object[] args = point.getArgs() ;
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods() ;
        String actiondesc="" ;
        for (Method method : methods)
        {
            if (method.getName().equals(methodName))
            {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length)
                {
                    actiondesc = method.getAnnotation(SystemAnnotationLog.class).actiondesc();
                    break;
                }
            }
        }
        return actiondesc ;
    }


    /***
     * 拦截控制层的操作日志，并写入数据库
     */
    @Around("logAspect()")//切入点结尾处切入内容
    public String recordLog(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Systemlog systemlog = new Systemlog();

        //获取到当前线程绑定的请求对象，得到session
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserInfo userInfo=(UserInfo)request.getSession().getAttribute("userinfo");
        systemlog.setUsername(userInfo.getUsername());
        systemlog.setRealname(userInfo.getRealname());
        systemlog.setDepname(userInfo.getDepname());

        //获取方法执行时间
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        systemlog.setOperatetime(format.format(date));

        systemlog.setIp(request.getRemoteAddr());//获取用户IP

        //利用UserAgent工具类进行User-Agent解析,得到操作系统类型和浏览器类型
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        systemlog.setBrowser(userAgent.getBrowser().toString());//浏览器名称
        systemlog.setOsname(userAgent.getOperatingSystem().toString());//操作系统名称
        systemlog.setMethodname(joinPoint.getSignature().getName());//执行的方法名称
        systemlog.setClassname(joinPoint.getTarget().getClass().getName());//获取类名称

        String strOperetetype=getControllerMethodActionType(joinPoint);
        systemlog.setOperetetype(strOperetetype);//注解描述的操作类型

        Object proceed=joinPoint.proceed();//Around注解必须执行该方法，否则被拦截的方法会不执行
        String strInfo=(String)proceed;

        //根据切点的args来获取目标对象的参数,转换为json方式
        Object[] args = joinPoint.getArgs();
        if(strOperetetype.indexOf("查询")>-1)
        {
            systemlog.setOperetedesc(JSON.toJSONString(args[0]));
            systemlogService.save(systemlog);
        }

        if(strOperetetype.indexOf("添加")>-1)
        {
            Map map = FilterPureEntity.getKeyAndValue(args[0]);
            systemlog.setOperetedesc(JSON.toJSONString(map));
            systemlogService.save(systemlog);
        }

        if(strOperetetype.indexOf("删除")>-1)
        {
            systemlog.setOperetedesc(strInfo);
            systemlogService.save(systemlog);
        }

        return strInfo;

    }



}
