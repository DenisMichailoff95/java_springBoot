package com.dma.springboot.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {

    private List<Predicate> predicates = new ArrayList<>();

    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    // Исправлено: используем накопленные предикаты
    public Predicate buildAnd() {
        return predicates.stream()
                .reduce(ExpressionUtils::allOf)
                .orElse(null);
    }

    // Исправлено: используем накопленные предикаты
    public Predicate buildOr() {
        return predicates.stream()
                .reduce(ExpressionUtils::anyOf)
                .orElse(null);
    }

    public static QPredicate builder() {
        return new QPredicate();
    }
}