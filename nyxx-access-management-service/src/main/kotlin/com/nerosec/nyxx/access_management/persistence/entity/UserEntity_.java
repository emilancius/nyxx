package com.nerosec.nyxx.access_management.persistence.entity;

import com.nerosec.nyxx.commons.persistence.entity.BaseEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserEntity.class)
public class UserEntity_ extends BaseEntity_ {

    public static final String PROP_USERNAME = "username";
    public static final String PROP_PASSPHRASE = "passphrase";
    public static final String PROP_TYPE = "type";
    public static final String PROP_STATE = "state";

    public static volatile SingularAttribute<UserEntity, String> username;
    public static volatile SingularAttribute<UserEntity, String> passphrase;
    public static volatile SingularAttribute<UserEntity, UserType> type;
    public static volatile SingularAttribute<UserEntity, UserState> state;
}