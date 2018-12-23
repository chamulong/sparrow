/**
 * @Author: 江成军
 * @Date: 2018/12/23 16:25
 * @Description: 通过JAVA反射机制动态加载通讯协议类
 */
package com.jcj.sparrow.NettyServerService.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtils
{
    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    public static Object getBean(String className)
    {
        Class clazz = null;
        try
        {
            clazz = Class.forName(className);
        }
        catch (Exception ex)
        {
            logger.info("找不到指定的协议类{}",className);
        }
        if (clazz != null)
        {
            try
            {
                return clazz.newInstance();
            }
            catch (Exception ex) {
                logger.info("找不到指定的协议类{}",className);
            }
        }
        return null;
    }
}

