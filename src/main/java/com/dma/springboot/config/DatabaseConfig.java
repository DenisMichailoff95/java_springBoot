package com.dma.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatabaseConfig {

//    @Profile({"dev", "test"})
//    @Bean
//    public DataSource h2DataSource() {
//        log.info("Configuring H2 DataSource for dev/test profiles");
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//        return dataSource;
//    }
//
//    @Profile("prod")
//    @Bean
//    public DataSource postgresqlDataSource() {
//        log.info("Configuring PostgreSQL DataSource for prod profile");
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5433/task_manager_alpine");
//        dataSource.setUsername("alpine_user");
//        dataSource.setPassword("alpine_user_pass");
//        return dataSource;
//    }
}