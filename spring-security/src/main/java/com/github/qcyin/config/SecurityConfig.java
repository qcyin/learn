package com.github.qcyin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("root")
                .password(passwordEncoder.encode("root"))
                .roles("root")
                .and()
                .passwordEncoder(passwordEncoder)
                .withUser("admin")
                .password(passwordEncoder.encode("123456"))
                .roles("admin")
        ;

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] antPatterns = new String[]{"/css/**", "/font/**", "/img/**", "/js/**"};
        web.ignoring().antMatchers(antPatterns);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/login/success")
                .failureUrl("/login/failure")
                .and()
                // https://www.cnblogs.com/felordcn/p/12142535.html
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .invalidateHttpSession(true) // 是否移除 HttpSession
                .clearAuthentication(true) // 是否在退出时清除当前用户的认证信息
        ;
    }
}
