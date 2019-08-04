package com.example.demo.controller

import com.example.demo.dto.req.UserReqDto
import com.example.demo.entity.User
import com.example.demo.service.contract.IAuthService
import org.hibernate.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"])
class AuthController(@Autowired val authService: IAuthService) {
    @GetMapping(value = ["/"])
    fun demo(@RequestParam search: String?, @RequestParam status: Int?): List<User> {
//        val dto = UserReqDto(
//                name = "Hoang",
//                email = "hoa1g@gmail.com",
//                password = "abc"
//        )
//        return authService.register(dto)
        val page = authService.findList(search, status)
        return page.content
    }
}