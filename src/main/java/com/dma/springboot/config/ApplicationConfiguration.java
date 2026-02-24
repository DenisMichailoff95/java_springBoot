package com.dma.springboot.config;

import com.dma.springboot.conditional.FirstConditional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Conditional(FirstConditional.class)
@Profile({"dev", "test", "prod"}) // Теперь работает для всех профилей
@Slf4j
@Configuration
public class ApplicationConfiguration {

    @PostConstruct
    public void init(){
        log.warn("Application configuration initialized");
    }
}