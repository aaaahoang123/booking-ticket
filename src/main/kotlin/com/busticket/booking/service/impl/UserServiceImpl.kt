package com.busticket.booking.service.impl

import com.busticket.booking.entity.User
import com.busticket.booking.lib.assignObject
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.user.UserRepository
import com.busticket.booking.repository.user.userEmailEqual
import com.busticket.booking.request.UserRequest
import com.busticket.booking.service.interfaces.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class UserServiceImpl @Autowired constructor(
        userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : UserService, BaseService<User, Int>() {
    init {
        this.primaryRepo = userRepository
    }
    override fun getInstanceClass(): KClass<User> {
        return User::class
    }

    override fun create(dto: Any): User {
        dto as UserRequest
        val existed = primaryRepo.count(
                Specification.where(userEmailEqual(dto.email))
        ) != 0L
        if (existed) {
            throw ExecuteException("duplicate_email")
        }
        dto.password = dto.password?.let { it -> passwordEncoder.encode(it) }
        return super.create(dto)
    }

    override fun edit(id: Int, dto: Any): User {
        dto as UserRequest
        dto.password = dto.password?.let { it -> passwordEncoder.encode(it) }
        val existed = singleById(id)
        return primaryRepo.save(assignObject(existed, dto))
    }
}