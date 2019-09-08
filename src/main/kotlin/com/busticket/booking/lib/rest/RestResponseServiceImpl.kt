package com.busticket.booking.lib.rest

import com.busticket.booking.lib.locale.LocaleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class RestResponseServiceImpl @Autowired constructor(
        private val localeService: LocaleService,
        private val request: HttpServletRequest
): RestResponseService {
    override fun restSuccess(data: Any?, meta: Any?, message: String): ResponseEntity<Any> {
        val res = mutableMapOf<String, Any?>(
                "status" to 1,
                "message" to localeService.getMessage(message, request)
        )
        if (data?.javaClass?.isArray == true || data?.javaClass?.let { it -> List::class.java.isAssignableFrom(it)} == true) {
            res["datas"] = data
        } else {
            res["data"] = data
        }
        if (meta != null) {
            res["meta"] = meta
        }
        return ResponseEntity(res, HttpStatus.OK)
    }

    override fun restError(message: String, status: HttpStatus, messageArgs: Array<Any>?): ResponseEntity<Any> {
        return ResponseEntity(mapOf(
                "timestamp" to Calendar.getInstance(),
                "status" to status.value(),
                "message" to localeService.getMessage(message, request, messageArgs)
        ), status)
    }

    override fun restException(e: Exception): ResponseEntity<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}