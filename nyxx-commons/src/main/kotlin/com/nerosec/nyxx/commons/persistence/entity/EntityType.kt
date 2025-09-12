package com.nerosec.nyxx.commons.persistence.entity

import java.util.UUID

enum class EntityType {
    USER,
    CONTACT,
    TOKEN;

    fun generateEntityId(): String = "$name.${UUID.randomUUID()}".uppercase()
}