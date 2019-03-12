package com.dk.services.messages

import com.dk.services.util.SingletonHolder
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import java.util.*
import javax.naming.Context


@Component
class Message (private val messageSource: MessageSource) {


    fun get(msgCode: String, loc: Locale?, vararg args:String?): String {
        val locale = LocaleContextHolder.getLocale()
        return messageSource.getMessage(msgCode, args, loc ?: locale)
    }

}