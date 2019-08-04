package com.example.demo.service.contract

import com.example.demo.dto.req.UserReqDto
import com.example.demo.entity.User
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface IAuthService {
    fun register(dto: UserReqDto): User
    fun findList(search: String?, status: Int?): Page<User>
}