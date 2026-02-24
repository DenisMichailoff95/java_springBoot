package com.dma.springboot.repository;

import com.dma.springboot.entity.CompanyEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("test")
//@ActiveProfiles("dev-file")
@ActiveProfiles("prod")
@Slf4j
@SpringBootTest
@Transactional
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private static final Integer APPLE_ID = 1;
    private static final Integer GOOGLE_ID = 2;

    @BeforeEach
    void setUp() {
        // Данные уже загружены из data.sql
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetById() {
        Optional<CompanyEntity> company = companyRepository.findById(APPLE_ID);
        assertTrue(company.isPresent());

        company.ifPresent(entity ->
                {
                    assertEquals("Apple", entity.getName());
                }
        );
    }

    @Test
    void testSave() {
        CompanyEntity company = CompanyEntity.builder()
                .name("fitBit")
                .build();
        companyRepository.save(company);
        assertNotNull(company.getId());
    }

    @Test
    void testCount() {
        long count = companyRepository.count();
        assertEquals(2, count); // Apple и Google из data.sql
    }
}