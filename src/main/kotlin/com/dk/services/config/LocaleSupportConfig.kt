package com.dk.services.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration
import org.springframework.web.server.i18n.LocaleContextResolver


@Configuration
open class LocaleSupportConfig : DelegatingWebFluxConfiguration() {

    override fun createLocaleContextResolver(): LocaleContextResolver {
        return CustomLocaleResolver()
    }

}