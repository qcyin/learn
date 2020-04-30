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
        web.ignoring().antMatchers("/static/**");
    }

    // https://www.jianshu.com/p/3bdea481e124
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // resources permit
                .antMatchers("/login", "/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // show login page
                .loginPage("/login.html")
                // name="username"
                .usernameParameter("username")
                // name="password"
                .passwordParameter("password")
                // post url
                .loginProcessingUrl("/login")
                // login success redirect url
                .defaultSuccessUrl("/index")
                // login failure redirect url
                .failureUrl("/login.html")
                .and()
                // https://www.cnblogs.com/felordcn/p/12142535.html
                .logout()
                // request url
                .logoutUrl("/logout")
                // logout success redirect url
                .logoutSuccessUrl("/login.html")
                // invalidate HttpSession
                .invalidateHttpSession(true)
                // clear Authenticated info
                .clearAuthentication(true)
                // delete Cookies
                .deleteCookies("JSESSIONID")
        ;
    }
}
