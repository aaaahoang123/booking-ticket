package com.busticket.booking.repository.policy

import com.busticket.booking.entity.MemberPolicy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MemberPolicyRepository: JpaRepository<MemberPolicy, Int>, JpaSpecificationExecutor<MemberPolicy> {
}