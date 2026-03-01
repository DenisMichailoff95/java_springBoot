package com.dma.springboot.repository;

import com.dma.springboot.dto.EmployeeFilter;
import com.dma.springboot.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeCustomRepository {

    List<EmployeeEntity> findCustomQuery();

    List<EmployeeEntity> findByFilter(EmployeeFilter filter);

}
