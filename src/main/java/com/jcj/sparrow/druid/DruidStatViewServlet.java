package com.jcj.sparrow.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @Author：江成军
 * @Description:druid数据源状态监控
 * @Date:Create in 2018/7/2 14:20
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                // IP白名单 (没有配置或者为空，则允许所有访问,多IP以逗号分隔，如value = "192.168.1.72,127.0.0.1")
                @WebInitParam(name = "allow", value = ""),
                // IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name = "deny", value = ""),
                // 用户名
                @WebInitParam(name = "loginUsername", value = "admin"),
                // 密码
                @WebInitParam(name = "loginPassword", value = "admin"),
                // 禁用HTML页面上的“Reset All”功能
                @WebInitParam(name = "resetEnable", value = "false")
        }
)
public class DruidStatViewServlet extends StatViewServlet
{
}
