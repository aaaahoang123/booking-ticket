package com.busticket.booking.lib.locale

import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.servlet.LocaleResolver
import java.util.*
import org.springframework.context.annotation.ScopedProxyMode

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class LocaleServiceImpl: LocaleService {
    @Autowired
    private lateinit var messageSource: MessageSource
    @Autowired
    private lateinit var localeResolver: LocaleResolver

    override fun getMessage(key: String, request: HttpServletRequest?, args: Array<Any>?): String {
        val locale = request?.let { localeResolver.resolveLocale(it) } ?: Locale.getDefault()
        val trueArg = args?.map { arg ->
            if (arg is String) {
                messageSource.getMessage(arg, null, locale)
            } else {
                arg
            }
        }?.toTypedArray()
        return messageSource.getMessage(key, trueArg, locale)
    }
}