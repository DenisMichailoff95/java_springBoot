package com.dma.springboot.conditional.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Value("${app.service.greeting.message}")
    private String greeting;

    public String greeting(){
        return greeting;
    }

}
