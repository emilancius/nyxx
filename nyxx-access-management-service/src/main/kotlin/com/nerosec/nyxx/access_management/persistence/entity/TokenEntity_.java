package com.nerosec.nyxx.access_management.persistence.entity;

import com.nerosec.nyxx.commons.persistence.entity.BaseEntity_;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.time.Instant;

@StaticMetamodel(TokenEntity.class)
public class TokenEntity_ extends BaseEntity_ {

    public static final String PROP_USER_ID = "userId";
    public static final String PROP_SESSION_ID = "sessionId";
    public static final String PROP_TYPE = "type";
    public static final String PROP_TOKEN = "token";
    public static final String PROP_STATE = "state";
    public static final String PROP_EXPIRES_AT = "expires";

    public static volatile SingularAttribute<TokenEntity, String> userId;
    public static volatile SingularAttribute<TokenEntity, String> sessionId;
    public static volatile SingularAttribute<TokenEntity, TokenType> type;
    public static volatile SingularAttribute<TokenEntity, String> token;
    public static volatile SingularAttribute<TokenEntity, TokenState> state;
    public static volatile SingularAttribute<TokenEntity, Instant> expires;
}