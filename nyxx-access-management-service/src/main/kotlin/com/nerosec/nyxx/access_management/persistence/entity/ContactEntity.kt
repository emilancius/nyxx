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
@Table(name = ContactEntity.TABLE_CONTACTS)
class ContactEntity() : BaseEntity() {

    companion object {
        const val TABLE_CONTACTS = "CONTACTS"

        const val COL_USER_ID = "USER_ID"
        const val COL_TYPE = "TYPE"
        const val COL_VALUE = "VALUE"
        const val COL_IS_PRIMARY = "IS_PRIMARY"
    }

    @Column(name = COL_USER_ID, nullable = false, updatable = false)
    var userId: String? = null

    @Column(name = COL_TYPE, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType? = null

    @Column(name = COL_VALUE, nullable = false)
    var value: String? = null

    @Column(name = COL_IS_PRIMARY, nullable = false)
    var primary: Boolean? = null

    constructor(
        id: String? = null,
        userId: String? = null,
        type: UserType? = null,
        value: String? = null,
        primary: Boolean? = null,
        etag: String? = null,
        created: Instant? = null,
        lastUpdated: Instant? = null
    ) : this() {
        this.id = id
        this.userId = userId
        this.type = type
        this.value = value
        this.primary = primary
        this.etag = etag
        this.created = created
        this.lastUpdated = lastUpdated
    }

    override fun getEntityType(): EntityType = EntityType.CONTACT

    fun copy(
        id: String? = this.id,
        userId: String? = this.userId,
        type: UserType? = this.type,
        value: String? = this.value,
        primary: Boolean? = this.primary,
        etag: String? = this.etag,
        created: Instant? = this.created,
        lastUpdated: Instant? = this.lastUpdated
    ): ContactEntity = ContactEntity(
        id = id,
        userId = userId,
        type = type,
        value = value,
        primary = primary,
        etag = etag,
        created = created,
        lastUpdated = lastUpdated
    )
}