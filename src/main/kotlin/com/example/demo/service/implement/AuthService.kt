package com.example.demo.service.implement

import com.example.demo.dto.req.UserReqDto
import com.example.demo.entity.User
import com.example.demo.repository.initSpec
import com.example.demo.repository.user.UserRepository
import com.example.demo.repository.user.userHasSearch
import com.example.demo.repository.user.userHasStatus
import com.example.demo.service.contract.IAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired val userRepository: UserRepository): IAuthService {
    override fun register(dto: UserReqDto): User {
        val user = User(
                name = dto.name,
                email = dto.email,
                password = dto.password
        )
        return userRepository.save(user)
    }

    override fun findList(search: String?, status: Int?): Page<User> {
        var spec: Specification<User> = initSpec()
        if (search != null)
            spec = spec.and(userHasSearch(search))
        if (status != null) {
            spec = spec.and(userHasStatus(status))
        }
        return userRepository.findAll(Specification.where(spec), PageRequest.of(0, 20))
    }
}