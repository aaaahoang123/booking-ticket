package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.AuthRequest
import com.busticket.booking.service.interfaces.AuthService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"])
class AuthController @Autowired constructor(
        private val authService: AuthService,
        private val restResponseService: RestResponseService,
        private val dtoBuilder: DtoBuilderService
) {

    @PostMapping(value = ["/login"])
    fun login(@RequestBody @Valid loginData: AuthRequest): ResponseEntity<Any> {
        val user = authService.attemptMember(loginData.email, loginData.password)
        val token = authService.generateToken(user)
        val data = dtoBuilder.buildUserDto(user, token)
        return restResponseService.restSuccess(data)
    }
}