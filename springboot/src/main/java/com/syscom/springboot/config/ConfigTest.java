package com.syscom.springboot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConfigTest {

    @Value("${server.port}")
    private String port;

    @Bean("aaa")
    public String s(){
        return String.valueOf(port);
    }

}
