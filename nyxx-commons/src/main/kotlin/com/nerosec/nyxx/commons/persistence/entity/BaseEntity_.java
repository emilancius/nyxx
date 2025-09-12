package com.nerosec.nyxx.commons.persistence.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.time.Instant;

@StaticMetamodel(BaseEntity.class)
public class BaseEntity_ {

    public static final String PROP_ID = "id";
    public static final String PROP_ETAG = "etag";
    public static final String PROP_CREATED_AT = "created";
    public static final String PROP_LAST_UPDATED_AT = "lastUpdated";

    public static volatile SingularAttribute<BaseEntity, String> id;
    public static volatile SingularAttribute<BaseEntity, String> etag;
    public static volatile SingularAttribute<BaseEntity, Instant> created;
    public static volatile SingularAttribute<BaseEntity, Instant> lastUpdated;
}