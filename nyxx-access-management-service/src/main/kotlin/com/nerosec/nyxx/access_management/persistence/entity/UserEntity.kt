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
@Table(name = UserEntity.TABLE_USERS)
class UserEntity() : BaseEntity() {

    companion object {
        const val TABLE_USERS = "USERS"

        const val COL_USERNAME = "USERNAME"
        const val COL_PASSPHRASE = "PASSPHRASE"
        const val COL_TYPE = "TYPE"
        const val COL_STATE = "STATE"
    }

    @Column(name = COL_USERNAME, nullable = false)
    var username: String? = null

    @Column(name = COL_PASSPHRASE, nullable = false)
    var passphrase: String? = null

    @Column(name = COL_TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType? = null

    @Column(name = COL_STATE, nullable = false)
    @Enumerated(EnumType.STRING)
    var state: UserState? = null

    constructor(
        id: String? = null,
        username: String? = null,
        passphrase: String? = null,
        type: UserType? = null,
        state: UserState? = null,
        etag: String? = null,
        created: Instant? = null,
        lastUpdated: Instant? = null
    ) : this() {
        this.id = id
        this.username = username
        this.passphrase = passphrase
        this.type = type
        this.state = state
        this.etag = etag
        this.created = created
        this.lastUpdated = lastUpdated
    }

    override fun getEntityType(): EntityType = EntityType.USER

    fun copy(
        id: String? = this.id,
        username: String? = this.username,
        passphrase: String? = this.passphrase,
        type: UserType? = this.type,
        state: UserState? = this.state,
        etag: String? = this.etag,
        created: Instant? = this.created,
        lastUpdated: Instant? = this.lastUpdated
    ): UserEntity = UserEntity(
        id = id,
        username = username,
        passphrase = passphrase,
        type = type,
        state = state,
        etag = etag,
        created = created,
        lastUpdated = lastUpdated
    )
}