package com.nerosec.nyxx.access_management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

typealias PassphraseEncoder = PasswordEncoder
typealias BCryptPassphraseEncoder = BCryptPasswordEncoder

@SpringBootApplication
class AccessManagementServiceApplication {

    @Bean
    fun passphraseEncoder(): PassphraseEncoder = BCryptPassphraseEncoder()
}

fun main(args: Array<String>) {
    runApplication<AccessManagementServiceApplication>(*args)
}