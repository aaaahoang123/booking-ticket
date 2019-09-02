package com.busticket.booking.lib.locale

import javax.servlet.http.HttpServletRequest

interface LocaleService {
    fun getMessage(key: String, request: HttpServletRequest? = null, args: Array<Any>? = null): String
}