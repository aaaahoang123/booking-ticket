package com.busticket.booking.lib.exception

import com.busticket.booking.lib.rest.RestResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AppExceptionHandler @Autowired constructor(
        private val restResponseService: RestResponseService
): ResponseEntityExceptionHandler()  {
    @ExceptionHandler(value = [UsernameNotFoundException::class])
    fun handleAuthException(e: UsernameNotFoundException): ResponseEntity<Any> {
        return restResponseService.restError(e.message ?: "failed", HttpStatus.UNAUTHORIZED)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val field = ex.bindingResult.fieldErrors[0]
        val fieldName = field.field
        val message = field.defaultMessage
        return restResponseService.restError(message!!, HttpStatus.NOT_FOUND, arrayOf(fieldName))
    }

    @ExceptionHandler(ExecuteException::class)
    fun handleDefaultException(e: ExecuteException): ResponseEntity<Any> {
        println("vcl")
        return restResponseService.restError(e.message ?: "failed", e.status, e.messageArgs)
    }

}