package com.dma.springboot.repository;

import com.dma.springboot.dto.EmployeeFilter;
import com.dma.springboot.entity.EmployeeEntity;
import com.dma.springboot.entity.QEmployeeEntity;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dma.springboot.entity.QEmployeeEntity.employeeEntity;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<EmployeeEntity> findCustomQuery() {

        return List.of();
    }

    @Override
    public List<EmployeeEntity> findByFilter(EmployeeFilter filter) {

        return new JPAQuery<EmployeeEntity>(entityManager)
                .select(QEmployeeEntity.employeeEntity)
                .from(QEmployeeEntity.employeeEntity)
                .where(QEmployeeEntity.employeeEntity.firstName.containsIgnoreCase(filter.getFirstName()))
                .fetch();
    }
}
