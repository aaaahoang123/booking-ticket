package com.busticket.booking.service.impl

import com.busticket.booking.entity.User
import com.busticket.booking.entity.UserPolicy
import com.busticket.booking.entity.UserRole
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.fetchRelation
import com.busticket.booking.repository.policy.UserPolicyRepository
import com.busticket.booking.repository.role.UserRoleRepository
import com.busticket.booking.repository.user.UserRepository
import com.busticket.booking.request.PolicyRequest
import com.busticket.booking.service.interfaces.PolicyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import javax.management.relation.Role
import kotlin.reflect.KClass

@Service
class PolicyServiceImpl @Autowired constructor(
        private val policyRepo: UserPolicyRepository,
        private val roleRepo: UserRoleRepository,
        private val userRepo: UserRepository
) : PolicyService, BaseService<UserPolicy, Int>() {
    override fun getInstanceClass(): KClass<UserPolicy> {
        return UserPolicy::class
    }

    init {
        primaryRepo = policyRepo
    }

    override fun create(dto: Any): UserPolicy {
        dto as PolicyRequest
        val policy = UserPolicy(
                name = dto.name!!,
                specialRole = 0,
                roles = roleRepo.findByIdIsIn(dto.roles!!).toSet()
        )
        return this.policyRepo.save(policy)
    }

    override fun edit(id: Int, dto: Any): UserPolicy {
        dto as PolicyRequest
        val policy = policyRepo.findById(id).get()
        policy.name = dto.name!!
        policy.roles = roleRepo.findByIdIsIn(dto.roles!!).toSet()
        return this.policyRepo.save(policy)
    }

    override fun listRoles(): List<UserRole> {
        return roleRepo.findAll()
    }

    override fun userJoinPolicy(userID: Int, policyId: Int): User {
        val oUser = this.userRepo.findById(userID)
        if (!oUser.isPresent) throw ExecuteException("not_found")
        val user = oUser.get()
        user.policy = policyRepo.findById(policyId).get()
        return userRepo.save(user)
    }

    override fun userOutPolicy(userID: Int): User {
        val oUser = this.userRepo.findById(userID)
        if (!oUser.isPresent) throw ExecuteException("not_found")
        val user = oUser.get()
        user.policy = null
        return userRepo.save(user)
    }

    override fun singleById(id: Int): UserPolicy {
//        val spec = Specification.where(fetchRelation<UserPolicy, Role>("roles"))
        return policyRepo.getByIdAndJoin(id).orElse(null)
    }
}