package com.busticket.booking.repository.policy

import com.busticket.booking.entity.UserPolicy
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserPolicyRepository: BaseRepository<UserPolicy, Int> {
}