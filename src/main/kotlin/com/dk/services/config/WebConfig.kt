package com.dk.services.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
open class WebConfig {

    @Bean
    open fun messageSource(): MessageSource {
        val rs = ResourceBundleMessageSource()
        rs.setBasename("i18n/messages")
        rs.setDefaultEncoding("UTF-8")
        //rs.setUseCodeAsDefaultMessage(true)
        return rs
    }

}