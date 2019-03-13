package com.dk.services

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KotlinDemoApplication

    fun main(args: Array<String>) {
        println("Starting Router 0.1.0")
        runApplication<KotlinDemoApplication>(*args)
    }


