package com.dma.springboot.repository;

import com.dma.springboot.entity.EmployeeEntity;
import com.dma.springboot.integrationTestBase;
import com.dma.springboot.projection.EmployeeNameView;
import com.dma.springboot.projection.EmployeeNativeView;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


@Slf4j
class EmployeeRepositoryTest extends integrationTestBase {

    private static final Integer IVAN_ID = 1;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void fiendById(){
        Optional<EmployeeEntity> employee = employeeRepository.findById(IVAN_ID);
        Assertions.assertTrue(employee.isPresent());
    }

    @Test
    void findByFirstNameContainingTest1() {
        Optional<EmployeeEntity> employee = employeeRepository.findByFirstNameContaining("va");

        // AssertJ стиль
        assertThat(employee).isPresent();

        employee.ifPresent(emp -> {
            log.warn("Found employee: {}", emp);
        });
    }

    @Test
    void findByFirstNameContainingTest2() {
        Optional<EmployeeEntity> employee = employeeRepository.findByFirstNameContaining("va");

        // AssertJ стиль
        assertThat(employee).isPresent();

        employee.ifPresent(emp -> {
            assertThat(emp.getFirstName()).containsIgnoringCase("va");
            log.warn("Found employee: {}", emp);
        });
    }

    @Test
    void findByQuery() {
        Optional<EmployeeEntity> employee = employeeRepository.findByFirstNameContaining("va");

        assertThat(employee).isPresent();

        employee.ifPresent(emp -> {
            assertThat(emp.getFirstName()).containsIgnoringCase("va");
            log.warn("Found employee: {}", emp);
        });
    }

    @Test
    void findByQuery2() {
        Optional<EmployeeEntity> employee = employeeRepository.findByQuery("Ivan");

        assertThat(employee).isPresent();

        employee.ifPresent(emp -> {
            log.warn("Found employee: {}", emp);
        });
    }

    @Test
    void findByQueryParam() {
        Optional<EmployeeEntity> employee = employeeRepository.findByQueryParm("Ivan");

        assertThat(employee).isPresent();

        employee.ifPresent(emp -> {
            log.warn("Found employee: {}", emp);
        });
    }

    @Test
    void findByNativeQuery() {
        Optional<EmployeeEntity> employee = employeeRepository.findByNativeQuery("Ivan");

        assertThat(employee).isPresent();

        employee.ifPresent(emp -> {
            log.warn("Found employee: {}", emp);
        });
    }

    @Test
    void findProjectionBySalaryGreaterThan() {
        List<EmployeeNameView> employees = employeeRepository.findBySalaryGreaterThan(100);

        // ============ ASSERTJ СТИЛЬ ============
        assertThat(employees)
                .isNotEmpty()
                .hasSize(2);

        // Проверка содержимого через AssertJ
        assertThat(employees)
                .extracting(EmployeeNameView::getFirstName)
                .contains("Ivan", "Petr");

        // Проверка каждого элемента через AssertJ
        employees.forEach(emp -> {
            assertThat(emp.getFirstName()).isNotNull();
            assertThat(emp.getLastName()).isNotNull();
            assertThat(emp.getFullName()).isNotBlank();
            log.warn("Employee projection: {} {}", emp.getFirstName(), emp.getLastName());
        });

        // Фильтрация через AssertJ
        assertThat(employees)
                .filteredOn(emp -> emp.getFirstName().equals("Ivan"))
                .hasSize(1);

        // ============ HAMCREST СТИЛЬ (через MatcherAssert) ============
        MatcherAssert.assertThat(employees, hasSize(2));
        MatcherAssert.assertThat(employees, not(empty()));

        // Проверка содержимого через Hamcrest
        MatcherAssert.assertThat(employees,
                Matchers.<EmployeeNameView>hasItems(
                        Matchers.hasProperty("firstName", is("Ivan")),
                        Matchers.hasProperty("firstName", is("Petr"))
                ));

        // ============ JUNIT ASSERTIONS ============
        assertEquals(2, employees.size());
        assertFalse(employees.isEmpty());
    }

    @Test
    void findProjectionBySalaryGreaterThanNative() {
        List<EmployeeNativeView> employees = employeeRepository.findBySalaryGreaterThanNative(100);

        // AssertJ стиль
        assertThat(employees).hasSize(2);

        // Hamcrest стиль
        MatcherAssert.assertThat(employees, hasSize(2));

        // Проверка через Hamcrest
        employees.forEach(emp -> {
            MatcherAssert.assertThat(emp.getFirstName(), notNullValue());
            MatcherAssert.assertThat(emp.getLastName(), notNullValue());
        });
    }

    @Test
    void testFindCustomQuery() {
        List<EmployeeEntity> customQuery = employeeRepository.findCustomQuery();

        // AssertJ стиль
        assertThat(customQuery).isEmpty();

        // Hamcrest стиль
        MatcherAssert.assertThat(customQuery, empty());

        // JUnit стиль
        assertTrue(customQuery.isEmpty());
        assertEquals(0, customQuery.size());
    }

    @Test
    void testFindByMultipleConditions() {
        // Пример комбинированного использования
        Optional<EmployeeEntity> employee = employeeRepository.findByFirstNameAndLastName("Ivan", "Ivanov");

        // AssertJ
        assertThat(employee).isPresent();

        // Hamcrest
        MatcherAssert.assertThat(employee.isPresent(), is(true));

        employee.ifPresent(emp -> {
            // AssertJ
            assertThat(emp.getFirstName()).isEqualTo("Ivan");
            assertThat(emp.getLastName()).isEqualTo("Ivanov");

            // Hamcrest
            MatcherAssert.assertThat(emp.getFirstName(), is("Ivan"));
            MatcherAssert.assertThat(emp.getLastName(), is("Ivanov"));

            // Комбинированная проверка
            assertAll(
                    () -> assertEquals("Ivan", emp.getFirstName()),
                    () -> MatcherAssert.assertThat(emp.getLastName(), is("Ivanov")),
                    () -> assertThat(emp.getSalary()).isPositive()
            );
        });
    }
}