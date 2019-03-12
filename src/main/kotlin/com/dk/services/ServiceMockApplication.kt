package com.dk.services

import com.dk.services.config.ConfigParam
import com.dk.services.config.StrEqFunction
import com.dk.services.config.StrLikeFunction
import com.github.odiszapc.nginxparser.NgxBlock
import com.github.odiszapc.nginxparser.NgxConfig
import com.github.odiszapc.nginxparser.NgxIfBlock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KotlinDemoApplication

    fun main(args: Array<String>) {
        println("Starting Router 0.1.0")
        runApplication<KotlinDemoApplication>(*args)
    }


