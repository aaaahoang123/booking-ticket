package com.busticket.booking.lib.locale

import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*
import javax.servlet.http.HttpServletRequest

class HeaderLocaleResolver: AcceptHeaderLocaleResolver() {
    private val _supportedLocale = listOf("en", "vi")
            .map { lang -> Locale(lang) }

    override fun resolveLocale(req: HttpServletRequest): Locale {
        val language = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE)
        if (language == null || language.isEmpty()) {
            return Locale.getDefault()
        }
        val list = Locale.LanguageRange.parse(language)
        return Locale.lookup(list, _supportedLocale)
    }
}