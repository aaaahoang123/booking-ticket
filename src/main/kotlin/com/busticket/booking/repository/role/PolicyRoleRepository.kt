package com.busticket.booking.repository.role

import com.busticket.booking.entity.MemberPolicyRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PolicyRoleRepository: JpaRepository<MemberPolicyRole, String>, JpaSpecificationExecutor<MemberPolicyRole> {
}