package com.dk.services.messages

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*


@Component
class Message (private val messageSource: MessageSource) {


    fun get(msgCode: String, loc: Locale?, vararg args:String?): String {
        val locale = LocaleContextHolder.getLocale()
        return messageSource.getMessage(msgCode, args, loc ?: locale)
    }

}