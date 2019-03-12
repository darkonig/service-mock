package com.dk.services.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.server.i18n.LocaleContextResolver
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration


@Configuration
open class LocaleSupportConfig : DelegatingWebFluxConfiguration() {

    override fun createLocaleContextResolver(): LocaleContextResolver {
        return CustomLocaleResolver()
    }

}