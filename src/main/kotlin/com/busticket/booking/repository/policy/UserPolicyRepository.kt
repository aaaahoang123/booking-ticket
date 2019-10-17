package com.busticket.booking.repository.policy

import com.busticket.booking.entity.UserPolicy
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserPolicyRepository: BaseRepository<UserPolicy, Int> {
    @Query("select p from UserPolicy p join fetch p.roles where p.id = :id")
    fun getByIdAndJoin(@Param("id") id: Int): Optional<UserPolicy>
}