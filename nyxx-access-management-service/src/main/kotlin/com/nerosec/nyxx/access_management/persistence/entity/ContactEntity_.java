package com.nerosec.nyxx.access_management.persistence.entity;

import com.nerosec.nyxx.commons.persistence.entity.BaseEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ContactEntity.class)
public class ContactEntity_ extends BaseEntity_ {

    public static final String PROP_USER_ID = "userId";
    public static final String PROP_TYPE = "type";
    public static final String PROP_VALUE = "value";
    public static final String PROP_IS_PRIMARY = "primary";

    public static volatile SingularAttribute<ContactEntity, String> userId;
    public static volatile SingularAttribute<ContactEntity, ContactType> type;
    public static volatile SingularAttribute<ContactEntity, String> value;
    public static volatile SingularAttribute<ContactEntity, Boolean> primary;
}