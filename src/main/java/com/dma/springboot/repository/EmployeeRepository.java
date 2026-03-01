package com.dma.springboot.repository;

import com.dma.springboot.entity.EmployeeEntity;
import com.dma.springboot.projection.EmployeeNameView;
import com.dma.springboot.projection.EmployeeNativeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends
        JpaRepository<EmployeeEntity, Integer>,
        EmployeeCustomRepository,
        QuerydslPredicateExecutor<EmployeeEntity> {

    Optional<EmployeeEntity> findByFirstNameContaining(String firstName);

    @Query("Select e from EmployeeEntity e where e.firstName = :firstName")
    Optional<EmployeeEntity> findByQuery(@Param("firstName") String firstName);

    @Query(value = "SELECT e.* FROM employee e WHERE e.first_name = :firstName", nativeQuery = true)
    Optional<EmployeeEntity> findByNativeQuery(@Param("firstName") String firstName);

    @Query("Select e from EmployeeEntity e where e.firstName = ?1")
    Optional<EmployeeEntity> findByQueryParm(String firstName);

    List<EmployeeNameView> findBySalaryGreaterThan(Integer salary);

    @Query(value = "Select " +
            "e.id as id, " +
            "e.first_name || ' ' || e.last_name as full_name " +
            " from employee e where e.salary > :salary", nativeQuery = true)
    List<EmployeeNativeView> findBySalaryGreaterThanNative(@Param("salary") Integer salary);

    Optional<EmployeeEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
