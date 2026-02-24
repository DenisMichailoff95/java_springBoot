package com.dma.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Configuration
public class DataDirectoryConfig {

    @PostConstruct
    public void init() {
        // Создаем папку для данных, если она не существует
        File dataDir = new File("./data");
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            if (created) {
                log.info("Created data directory: {}", dataDir.getAbsolutePath());
            }
        }

        // Также создаем папку для тестовых данных
        File testDataDir = new File("./target");
        if (!testDataDir.exists()) {
            testDataDir.mkdirs();
        }
    }
}