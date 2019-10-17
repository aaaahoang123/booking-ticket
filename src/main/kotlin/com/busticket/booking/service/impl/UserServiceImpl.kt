package com.busticket.booking.service.impl

import com.busticket.booking.entity.User
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.lib.assignObject
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.hasStatus
import com.busticket.booking.repository.role.UserRoleRepository
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
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val userRoleRepository: UserRoleRepository
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

    override fun fetchRolesForUser(user: User): User {
        val policy = user.policy
        if (policy?.specialRole == ADMIN_SPECIAL_ROLE) {
            policy.roles = userRoleRepository.findAll(Specification.where(hasStatus())).toSet()
        }
        user.policy = policy
        return user
    }
}
