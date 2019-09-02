package com.busticket.booking.database.seeder

import com.busticket.booking.entity.UserPolicy
import com.busticket.booking.entity.UserRole
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.enum.role.ROLE_MANAGER_VOYAGE
import com.busticket.booking.enum.role.getRoleName
import com.busticket.booking.repository.user.UserRepository
import com.busticket.booking.repository.policy.UserPolicyRepository
import com.busticket.booking.repository.role.UserRoleRepository
import com.busticket.booking.request.AuthRequest
import com.busticket.booking.service.interfaces.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class PolicySeeder @Autowired constructor (
        private val policyRepository: UserPolicyRepository,
        private val roleRepository: UserRoleRepository,
        private val authService: AuthService,
        private val userRepository: UserRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (roleRepository.count() != 0L) {
            return
        }
        val roles = listOf(
                ROLE_MANAGER_VOYAGE
        ).map { role -> UserRole(id = role, name = getRoleName(role)) }.toSet()

        if (policyRepository.count() == 0L) {
            val adminPolicy = policyRepository.save(UserPolicy(name = "Admin", specialRole = ADMIN_SPECIAL_ROLE))
            policyRepository.save(UserPolicy(name = "Manager", roles = roles))
            val member = authService.register(AuthRequest("admin@gmail.com", "123456"))
            member.policy = adminPolicy
            userRepository.save(member)
        } else {
            roles.forEach { r -> roleRepository.save(r) }
        }

    }
}