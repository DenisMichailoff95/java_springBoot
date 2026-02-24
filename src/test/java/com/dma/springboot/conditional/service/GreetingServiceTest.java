package com.dma.springboot.conditional.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class GreetingServiceTest {

    @Autowired
    private GreetingService greetingService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void testGreeting(){
        String expectedMessage = "Hello";
        log.warn("greeting: " + greetingService.greeting());
        assertEquals(expectedMessage, greetingService.greeting());
    }
}