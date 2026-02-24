package com.dma.springboot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeEntity is a Querydsl query type for EmployeeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeEntity extends EntityPathBase<EmployeeEntity> {

    private static final long serialVersionUID = -1896667632L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeEntity employeeEntity = new QEmployeeEntity("employeeEntity");

    public final DatePath<java.time.LocalDate> birthDay = createDate("birthDay", java.time.LocalDate.class);

    public final QCompanyEntity company;

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath lastName = createString("lastName");

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public QEmployeeEntity(String variable) {
        this(EmployeeEntity.class, forVariable(variable), INITS);
    }

    public QEmployeeEntity(Path<? extends EmployeeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeEntity(PathMetadata metadata, PathInits inits) {
        this(EmployeeEntity.class, metadata, inits);
    }

    public QEmployeeEntity(Class<? extends EmployeeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompanyEntity(forProperty("company")) : null;
    }

}

