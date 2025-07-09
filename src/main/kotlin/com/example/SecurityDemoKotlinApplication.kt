package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityDemoKotlinApplication

fun main(args: Array<String>) {
    runApplication<SecurityDemoKotlinApplication>(*args)
}
