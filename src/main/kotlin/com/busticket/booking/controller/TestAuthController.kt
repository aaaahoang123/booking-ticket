package com.busticket.booking.controller

import com.busticket.booking.entity.User
import com.busticket.booking.enum.role.ROLE_MANAGER_VOYAGE
import com.busticket.booking.service.interfaces.AuthService
import com.busticket.booking.lib.auth.ReqUser
import com.busticket.booking.lib.rest.RestResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.access.annotation.Secured

@RestController
@RequestMapping(value = ["/api"])
class TestAuthController @Autowired constructor(
        private val authService: AuthService,
        private val restResponseService: RestResponseService
) {
    @GetMapping("/test1")
    @Secured(ROLE_MANAGER_VOYAGE)
    fun test1(@ReqUser user: User): Any {
        return restResponseService.restSuccess(mapOf("email" to user.email))
    }
}