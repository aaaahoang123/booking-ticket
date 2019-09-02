package com.busticket.booking.controller

import com.busticket.booking.request.AuthRequest
import com.busticket.booking.service.interfaces.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/auth"])
class TestController @Autowired constructor(
        private val authService: AuthService
) {
    @PostMapping(value = ["/register"])
    fun testRegister(@RequestBody @Valid dto: AuthRequest): String {
        val u = authService.register(dto)
        return authService.generateToken(u)
    }

    @GetMapping(value = ["oh-no"])
    fun ohNo(): String {
        return "abc"
    }

    @PostMapping(value = ["/login"])
    fun testLogin(@RequestBody dto: AuthRequest): String {
        val member = authService.attemptMember(dto.email, dto.password)
        return authService.generateToken(member)
    }
}