package com.jcj.sparrow.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author：江成军
 * @Description:注册访问/login转向login.html
 * @Date:Create in 2018/5/18 15:10
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}
