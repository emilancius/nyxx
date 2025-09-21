package com.nerosec.nyxx.commons.extensions

import com.nerosec.nyxx.commons.CharacterType
import com.nerosec.nyxx.commons.extensions.CharExtensions.getType
import com.nerosec.nyxx.commons.persistence.entity.EntityType

object StringExtensions {

    fun String.isEntityId(vararg entityTypes: EntityType): Boolean {
        val entityTypes = if (entityTypes.isEmpty()) EntityType.entries.toSet() else entityTypes.toSet()
        val entityTypesRegexGroup = "(${entityTypes.joinToString("|") { it.value }})"
        return matches(Regex("$entityTypesRegexGroup.[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}"))
    }

    fun String.containsCharByType(type: CharacterType): Boolean = any { type == it.getType() }
}