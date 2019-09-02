package com.busticket.booking.repository.policy

import com.busticket.booking.entity.UserPolicy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserPolicyRepository: JpaRepository<UserPolicy, Int>, JpaSpecificationExecutor<UserPolicy> {
}