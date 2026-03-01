package com.dma.springboot;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

//@ActiveProfiles("dev-file")
@Slf4j
@SpringBootTest
@Transactional
//@ActiveProfiles("dev-file")
@ActiveProfiles("prod")
public abstract class IntegrationTestBase {
}
