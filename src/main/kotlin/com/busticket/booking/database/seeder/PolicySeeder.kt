package com.busticket.booking.database.seeder

import com.busticket.booking.entity.MemberPolicy
import com.busticket.booking.entity.MemberPolicyRole
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.enum.role.ROLE_MANAGER_VOYAGE
import com.busticket.booking.enum.role.getRoleName
import com.busticket.booking.repository.member.MemberRepository
import com.busticket.booking.repository.policy.MemberPolicyRepository
import com.busticket.booking.repository.role.PolicyRoleRepository
import com.busticket.booking.request.AuthRequest
import com.busticket.booking.service.interfaces.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class PolicySeeder @Autowired constructor (
    private val policyRepository: MemberPolicyRepository,
    private val roleRepository: PolicyRoleRepository,
    private val authService: AuthService,
    private val memberRepository: MemberRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (roleRepository.count() != 0L) {
            return
        }
        val roles = listOf(
                ROLE_MANAGER_VOYAGE
        ).map { role -> roleRepository.save(MemberPolicyRole(id = role, name = getRoleName(role))) }.toSet()

        val adminPolicy = policyRepository.save(MemberPolicy(name = "Admin", specialRole = ADMIN_SPECIAL_ROLE))
        policyRepository.save(MemberPolicy(name = "Manager", roles = roles))
        val member = authService.register(AuthRequest("admin@gmail.com", "123456"))
        member.policy = adminPolicy
        memberRepository.save(member)
    }
}