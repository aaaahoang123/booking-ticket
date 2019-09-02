package com.busticket.booking.repository.role

import com.busticket.booking.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRoleRepository: JpaRepository<UserRole, String>, JpaSpecificationExecutor<UserRole> {
}