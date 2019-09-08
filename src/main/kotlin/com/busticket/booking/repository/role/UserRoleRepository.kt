package com.busticket.booking.repository.role

import com.busticket.booking.entity.UserRole
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRoleRepository: BaseRepository<UserRole, String> {
}