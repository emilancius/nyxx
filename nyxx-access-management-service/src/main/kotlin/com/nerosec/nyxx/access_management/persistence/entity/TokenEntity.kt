package com.nerosec.nyxx.access_management.persistence.entity

import com.nerosec.nyxx.commons.persistence.entity.BaseEntity
import com.nerosec.nyxx.commons.persistence.entity.EntityType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = TokenEntity.TABLE_TOKENS)
class TokenEntity() : BaseEntity() {

    companion object {
        const val TABLE_TOKENS = "TOKENS"

        const val COL_USER_ID = "USER_ID"
        const val COL_SESSION_ID = "SESSION_ID"
        const val COL_TYPE = "TYPE"
        const val COL_TOKEN = "TOKEN"
        const val COL_STATE = "STATE"
        const val COL_EXPIRES_AT = "EXPIRES_AT"
    }

    @Column(name = COL_USER_ID, nullable = false, updatable = false)
    var userId: String? = null

    @Column(name = COL_SESSION_ID, nullable = false, updatable = false)
    var sessionId: String? = null

    @Column(name = COL_TYPE, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var type: TokenType? = null

    @Column(name = COL_TOKEN, nullable = false, updatable = false)
    var token: String? = null

    @Column(name = COL_STATE, nullable = false)
    @Enumerated(EnumType.STRING)
    var state: TokenState? = null

    @Column(name = COL_EXPIRES_AT, nullable = false, updatable = false)
    var expires: Instant? = null

    constructor(
        id: String? = null,
        userId: String? = null,
        sessionId: String? = null,
        type: TokenType? = null,
        token: String? = null,
        state: TokenState? = null,
        expires: Instant? = null,
        etag: String? = null,
        created: Instant? = null,
        lastUpdated: Instant? = null
    ) : this() {
        this.id = id
        this.userId = userId
        this.sessionId = sessionId
        this.type = type
        this.token = token
        this.state = state
        this.expires = expires
        this.etag = etag
        this.created = created
        this.lastUpdated = lastUpdated
    }

    override fun getEntityType(): EntityType = EntityType.TOKEN

    fun copy(
        id: String? = this.id,
        userId: String? = this.userId,
        sessionId: String? = this.sessionId,
        type: TokenType? = this.type,
        token: String? = this.token,
        state: TokenState? = this.state,
        expires: Instant? = this.expires,
        etag: String? = this.etag,
        created: Instant? = this.created,
        lastUpdated: Instant? = this.lastUpdated
    ): TokenEntity = TokenEntity(
        id = id,
        userId = userId,
        sessionId = sessionId,
        type = type,
        token = token,
        state = state,
        expires = expires,
        etag = etag,
        created = created,
        lastUpdated = lastUpdated
    )
}