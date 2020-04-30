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
        web.ignoring().antMatchers("/static/images/login2.png");
    }

    // https://www.jianshu.com/p/3bdea481e124
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // resources permit
                .antMatchers("/**","/login", "/login.html", "/static/images/login2.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")               // show login page
                .usernameParameter("username")          // name="username"
                .passwordParameter("password")          // name="password"
                .loginProcessingUrl("/login")           // post url
                .defaultSuccessUrl("/index")            // login success redirect url
                .failureUrl("/login.html?error")      // login failure redirect url
                .and()
                // https://www.cnblogs.com/felordcn/p/12142535.html
                .logout()
                .logoutUrl("/logout")                   // request url
                .logoutSuccessUrl("/login.html")       // logout success redirect url
                .invalidateHttpSession(true)            // invalidate HttpSession
                .clearAuthentication(true)              // clear Authenticated info
                .deleteCookies("JSESSIONID")           // delete Cookies
        ;
    }
}
