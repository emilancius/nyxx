package com.nerosec.nyxx.commons.persistence.entity

import java.util.UUID

enum class EntityType(val value: String) {
    USER("USER"),
    CONTACT("CONTACT"),
    TOKEN("TOKEN");

    fun generateEntityId(): String = "$name.${UUID.randomUUID()}".uppercase()
}