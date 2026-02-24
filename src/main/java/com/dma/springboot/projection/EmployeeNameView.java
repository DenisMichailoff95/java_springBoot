package com.dma.springboot.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeNameView {

    String getFirstName();

    String getLastName();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

//    Что делает default?
//    Раньше (до Java 8):
//    В интерфейсах можно было только объявлять методы без реализации
//    Все методы были public abstract по умолчанию
//    При добавлении нового метода в интерфейс, все классы, реализующие этот интерфейс, ломались
//    Сейчас (Java 8+):
//    default методы позволяют добавить реализацию прямо в интерфейс
//    Классы, реализующие интерфейс, автоматически получают эту реализацию
//    Можно не переопределять default метод (но можно, если нужно)

//    default String getFullName() {
//        return getFirstName() + " " + getLastName();
//    }
}
