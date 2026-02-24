package com.dma.springboot.repository;

import com.dma.springboot.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<EmployeeEntity> findCustomQuery() {
        return List.of();
    }
}
