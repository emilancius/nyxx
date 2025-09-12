package com.nerosec.nyxx.commons.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.Instant

@MappedSuperclass
abstract class BaseEntity {

    companion object {
        const val COL_ID = "ID"
        const val COL_ETAG = "ETAG"
        const val COL_CREATED_AT = "CREATED_AT"
        const val COL_LAST_UPDATED_AT = "LAST_UPDATED_AT"
    }

    @Id
    @Column(name = COL_ID, nullable = false, updatable = false)
    var id: String? = null

    @Column(name = COL_ETAG, nullable = false)
    var etag: String? = null

    @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
    var created: Instant? = null

    @Column(name = COL_LAST_UPDATED_AT)
    var lastUpdated: Instant? = null

    abstract fun getEntityType(): EntityType

    @PrePersist
    fun beforeCreate() {
        if (id == null) id = getEntityType().generateEntityId()
        if (etag == null) etag = generateEtag()
        if (created == null) created = Instant.now()
    }

    @PreUpdate
    fun beforeUpdate() {
        etag = generateEtag()
        lastUpdated = Instant.now()
    }

    private fun generateEtag(): String = System.nanoTime().toString()
}