package com.dma.springboot.repository;

import com.dma.springboot.IntegrationTestBase;
import com.dma.springboot.dto.EmployeeFilter;
import com.dma.springboot.entity.EmployeeEntity;
import com.dma.springboot.projection.EmployeeNameView;
import com.dma.springboot.projection.EmployeeNativeView;
import com.dma.springboot.util.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.dma.springboot.entity.QEmployeeEntity.employeeEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


@Slf4j
class EmployeeRepositoryTest extends IntegrationTestBase {

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


    @Test
    void testFindByFilter() {

        EmployeeFilter filter = EmployeeFilter.builder()
                .firstName("ivan")
                .build();
        List<EmployeeEntity> employees = employeeRepository.findByFilter(filter);

        assertEquals(1, employees.size());

        employees.stream()
                .findFirst()
                .ifPresentOrElse(
                        employee -> log.info("Найден сотрудник: {}", employee),
                        () -> log.warn("Сотрудник не найден, хотя ожидался")
                );

        for (EmployeeEntity employee : employees) {
            log.info("Найден сотрудник: {}", employee);
            // Здесь можно добавить дополнительные проверки для каждого
            assertThat(employee.getFirstName()).containsIgnoringCase("ivan");
        }
    }

    @Test
    void testQPredicates() {
        // Given - используем реальные значения из БД
        EmployeeFilter filter = EmployeeFilter.builder()
                .firstName("ivan")
                .salary(50)  // Ищем зарплату >= 50 (в БД salary = 100)
                .build();

        log.info("=".repeat(60));
        log.info("🔍 Поиск по фильтру:");
        log.info("   firstName: '{}' (поиск по вхождению, без учета регистра)", filter.getFirstName());
        log.info("   salary >= {}", filter.getSalary());
        log.info("=".repeat(60));

        // When
        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstName(), employeeEntity.firstName::containsIgnoreCase)
                .add(filter.getSalary(), employeeEntity.salary::goe)
                .buildAnd();

        Iterable<EmployeeEntity> result = employeeRepository.findAll(predicate);
        List<EmployeeEntity> employees = new ArrayList<>();
        result.forEach(employees::add);

        // Then
        assertThat(employees)
                .as("Должен найти сотрудника Ivan с зарплатой >= %d", filter.getSalary())
                .isNotEmpty();

        log.info("✅ Найдено сотрудников: {}", employees.size());

        employees.forEach(emp -> {
            log.info("   → id={}: {} {}, зарплата={}, компания={}",
                    emp.getId(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getSalary(),
                    emp.getCompany() != null ? emp.getCompany().getName() : "—");

            // Проверяем каждого сотрудника
            assertThat(emp.getFirstName())
                    .as("Имя должно содержать 'ivan' (без учета регистра)")
                    .containsIgnoringCase("ivan");

            assertThat(emp.getSalary())
                    .as("Зарплата должна быть >= %d", filter.getSalary())
                    .isGreaterThanOrEqualTo(filter.getSalary());
        });

        log.info("=".repeat(60));
    }

    private String formatEmployee(EmployeeEntity emp) {
        return String.format("%s %s (з/п: %d, компания: %s)",
                emp.getFirstName(),
                emp.getLastName(),
                emp.getSalary(),
                emp.getCompany() != null ? emp.getCompany().getName() : "—"
        );
    }

}