package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.entity.User
import com.busticket.booking.lib.auth.ReqUser
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["$API_PREFIX/users"])
class UserController @Autowired constructor(
        private val dtoBuilderService: DtoBuilderService,
        private val responseService: RestResponseService
) {
    @GetMapping(value = ["/user-data"])
    @Secured
    fun userData(@ReqUser user: User, req: HttpServletRequest): ResponseEntity<Any> {
        return responseService.restSuccess(dtoBuilderService.buildUserDto(user, req.getHeader(AUTHORIZATION) as String))
    }
}