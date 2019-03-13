package com.dk.services.config

import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.SimpleLocaleContext
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver
import java.util.Locale


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