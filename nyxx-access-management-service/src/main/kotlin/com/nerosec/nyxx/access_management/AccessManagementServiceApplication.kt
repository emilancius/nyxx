package com.nerosec.nyxx.access_management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccessManagementServiceApplication

fun main(args: Array<String>) {
    runApplication<AccessManagementServiceApplication>(*args)
}