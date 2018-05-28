package com.tangyuan;

import com.tangyuan.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 作者：sunna
 * 时间: 2018/4/8 15:44
 */
@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*
        临时关闭权限校验
         */
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll();

        /*http.authorizeRequests()
                .antMatchers("/add_user").access("hasRole('USER')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll() //登录页面任意访问
                .and()
                .logout().permitAll(); //注销行为任意访问*/
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //使用BCryptPasswordEncoder进行秘密校验，同时要确保存储秘密的时候也要BCryptPasswordEncoder加密
        auth.userDetailsService(new UserDetailsServiceImpl()).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 在内存中建立一个用户，角色为READER、密码是password 账号是user
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("sun").password("123").roles("USER");
    }


}
