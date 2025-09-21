package com.nerosec.nyxx.access_management.service

import com.nerosec.nyxx.access_management.PassphraseEncoder
import com.nerosec.nyxx.access_management.persistence.entity.UserEntity
import com.nerosec.nyxx.access_management.persistence.entity.UserEntity_
import com.nerosec.nyxx.access_management.persistence.entity.UserState
import com.nerosec.nyxx.access_management.persistence.entity.UserType
import com.nerosec.nyxx.access_management.persistence.repository.UserRepository
import com.nerosec.nyxx.commons.CharacterType
import com.nerosec.nyxx.commons.extensions.StringExtensions.containsCharByType
import com.nerosec.nyxx.commons.persistence.entity.EntityType
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireArgument
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireArgumentIsSpecified
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireState
import com.nerosec.nyxx.commons.service.BaseService
import jakarta.persistence.metamodel.SingularAttribute
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passphraseEncoder: PassphraseEncoder
) : BaseService<UserEntity>(
    userRepository
) {
    companion object {
        val DEFAULT_TYPE = UserType.USER
        val DEFAULT_STATE = UserState.INACTIVE

        const val MIN_USERNAME_LENGTH = 4
        const val MAX_USERNAME_LENGTH = 32
        const val MIN_PASSPHRASE_LENGTH = 12

        val USERNAME_PRECONDITIONS = mapOf<Function1<String, Boolean>, String>(
            { username: String -> username.length in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH }
                    to "User's username must be $MIN_USERNAME_LENGTH to $MAX_USERNAME_LENGTH characters long.",
            { username: String -> username.matches(Regex("[A-Z0-9\\-_]", RegexOption.IGNORE_CASE)) }
                    to "User's username can only contain case-insensitive A-Z, 0-9, '-' and '_' characters.",
            { username: String -> !username[0].toString().matches(Regex("[-_]")) } to "User's username cannot start by '-' or '_' characters.",
            { username: String -> !username.contains(Regex("(-{2,})")) } to "User's username cannot contain consecutive hyphen ('-') characters."
        )
        val PASSPHRASE_PRECONDITIONS = mapOf<Function1<String, Boolean>, String>(
            { passphrase: String -> passphrase.length > (MIN_PASSPHRASE_LENGTH - 1) } to "User's passphrase cannot be less than $MIN_PASSPHRASE_LENGTH characters long.",
            { passphrase: String -> passphrase.containsCharByType(CharacterType.LC_LETTER) }
                    to "User's passphrase must contain character of type \"${CharacterType.LC_LETTER.name}\".",
            { passphrase: String -> passphrase.containsCharByType(CharacterType.UC_LETTER) }
                    to "User's passphrase must contain character of type \"${CharacterType.UC_LETTER.name}\".",
            { passphrase: String -> passphrase.containsCharByType(CharacterType.DIGIT) } to "User's passphrase must contain character of type \"${CharacterType.DIGIT.name}\".",
            { passphrase: String -> passphrase.containsCharByType(CharacterType.SPECIAL) } to "User's passphrase must contain character of type \"${CharacterType.SPECIAL.name}\"."
        )
    }

    override fun getEntityType(): EntityType = EntityType.USER

    override fun getPropertiesToSortBy(): Set<String> = setOf(
        UserEntity_.PROP_ID,
        UserEntity_.PROP_USERNAME,
        UserEntity_.PROP_TYPE,
        UserEntity_.PROP_STATE,
        UserEntity_.PROP_ETAG,
        UserEntity_.PROP_CREATED_AT,
        UserEntity_.PROP_LAST_UPDATED_AT
    )

    override fun getPropertiesToQueryBy(): Set<String> = setOf(
        UserEntity_.PROP_ID,
        UserEntity_.PROP_USERNAME,
        UserEntity_.PROP_TYPE,
        UserEntity_.PROP_STATE,
        UserEntity_.PROP_ETAG
    )

    override fun getPropertyTypes(): Map<String, SingularAttribute<in UserEntity, out Any>> = mapOf(
        UserEntity_.PROP_ID to UserEntity_.id,
        UserEntity_.PROP_USERNAME to UserEntity_.username,
        UserEntity_.PROP_PASSPHRASE to UserEntity_.passphrase,
        UserEntity_.PROP_TYPE to UserEntity_.type,
        UserEntity_.PROP_STATE to UserEntity_.state,
        UserEntity_.PROP_ETAG to UserEntity_.etag,
        UserEntity_.PROP_CREATED_AT to UserEntity_.created,
        UserEntity_.PROP_LAST_UPDATED_AT to UserEntity_.lastUpdated
    )

    override fun getListSpecification(queryBy: Map<String, Any?>): Specification<UserEntity>? = null

    @Transactional
    fun createUser(
        username: String,
        passphrase: String,
        type: UserType = DEFAULT_TYPE,
        state: UserState = DEFAULT_STATE
    ): UserEntity {
        requireArgumentIsSpecified("username", username)
        USERNAME_PRECONDITIONS.forEach { (precondition, message) -> requireArgument(precondition.invoke(username)) { message } }
        requireArgumentIsSpecified("passphrase", passphrase)
        PASSPHRASE_PRECONDITIONS.forEach { (precondition, message) -> requireArgument(precondition.invoke(passphrase)) { message } }
        requireState(!isUsernameTaken(username)) { "User \"$username\" could not be created: user by such username exists." }
        return userRepository.save(
            UserEntity(
                username = username,
                passphrase = passphraseEncoder.encode(passphrase),
                type = type,
                state = state
            )
        )
    }

    private fun isUsernameTaken(username: String): Boolean = userRepository.existsByUsername(username)
}