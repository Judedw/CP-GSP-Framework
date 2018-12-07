package com.clearpicture.platform.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCreateModifyAwareBaseEntity is a Querydsl query type for CreateModifyAwareBaseEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QCreateModifyAwareBaseEntity extends EntityPathBase<CreateModifyAwareBaseEntity> {

    private static final long serialVersionUID = -643250564L;

    public static final QCreateModifyAwareBaseEntity createModifyAwareBaseEntity = new QCreateModifyAwareBaseEntity("createModifyAwareBaseEntity");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> createdUserId = createNumber("createdUserId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> lastModifiedUserId = createNumber("lastModifiedUserId", Long.class);

    public QCreateModifyAwareBaseEntity(String variable) {
        super(CreateModifyAwareBaseEntity.class, forVariable(variable));
    }

    public QCreateModifyAwareBaseEntity(Path<? extends CreateModifyAwareBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCreateModifyAwareBaseEntity(PathMetadata metadata) {
        super(CreateModifyAwareBaseEntity.class, metadata);
    }

}

