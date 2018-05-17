package com.jcj.sparrow.utils;

import com.jcj.sparrow.service.UserinfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 * @Author：江成军
 * @Description:Spring Security配置类
 * @Date:Create in 2018/5/17 9:08
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Bean
    UserDetailsService customUserService()
    {
        return new UserinfoService();
    }


    /*  重要说明
        1.首先当我们要自定义Spring Security的时候我们需要继承自WebSecurityConfigurerAdapter来完成，相关配置重写对应 方法即可。
        2.我们在这里注册CustomUserService的Bean，然后通过重写configure方法添加我们自定义的认证方式。
        3.在configure(HttpSecurity http)方法中，我们设置了登录页面，而且登录页面任何人都可以访问，然后设置了登录失败地址，也设置了注销请求，注销请求也是任何人都可以访问的。
        4.permitAll表示该请求任何人都可以访问，.anyRequest().authenticated(),表示其他的请求都必须要有权限认证。
        5.这里我们可以通过匹配器来匹配路径，比如antMatchers方法，假设我要管理员才可以访问admin文件夹下的内容，我可以这样来写：.antMatchers("/admin/**").hasRole("ROLE_ADMIN")，也可以设置admin文件夹下的文件可以有多个角色来访问，写法如下：.antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ROLE_USER")
        6.可以通过hasIpAddress来指定某一个ip可以访问该资源,假设只允许访问ip为210.210.210.210的请求获取admin下的资源，写法如下.antMatchers("/admin/**").hasIpAddress("210.210.210.210")

        更多的权限控制方式参看下表：
           1)access(String)         SpringEL表达式结果为true时可以访问
           2)anonymous()            匿名可访问
           3)denyAll()              用户不可访问
           4)fullyAuthenticated     用户完全认证可访问（非remember me下自动登陆）
           5)hasAnyAuthority(String ...) 参数中任意权限的用户可访问
           6)hasAnyRole(String ...) 参数中任意角色的用户可访问
           7)hasAnyAuthority(String) 某一权限的用户可访问
           8)hasRole(String)         某一角色的用户可访问
           9)permitAll()             所有用户可访问
           10)rememberMe()           允许通过remember me登陆的用户访问
           11)authenticated()        用户登陆后可访问
           12)haslpAddress(String)   用户来自参数中的IP时可访问
     */



    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();// 关闭csrf防护

        http.authorizeRequests()            // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login.html","/custom/**","/Hplus/**").permitAll()     // 设置所有人都可以访问的登录页、静态资源
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login.html")       // 设置登录页面
                .defaultSuccessUrl("/index").permitAll().and() // 登录成功跳转路径url
                .logout().permitAll();

        http.logout().logoutSuccessUrl("/"); // 退出默认跳转页面
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //AuthenticationManager使用我们的 lightSwordUserDetailService 来获取用户信息
        auth.userDetailsService(userDetailsService());
    }

}
