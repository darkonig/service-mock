package com.dk.services.config

import com.dk.services.messages.Message
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.support.ResourceBundleMessageSource
import java.util.*
import java.util.Locale
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver
import org.springframework.web.server.i18n.FixedLocaleContextResolver
import org.springframework.web.server.i18n.LocaleContextResolver
import org.springframework.context.i18n.SimpleLocaleContext


open class CustomLocaleResolver: AcceptHeaderLocaleContextResolver() {

    override fun resolveLocaleContext(exchange: ServerWebExchange): LocaleContext {
        var targetLocale: Locale = Locale.getDefault()
        val referLang = exchange.request.headers.acceptLanguage
        if (referLang != null && !referLang.isEmpty()) {
            targetLocale = Locale.forLanguageTag(referLang[0].range)
        }

        return SimpleLocaleContext(targetLocale)
    }

}