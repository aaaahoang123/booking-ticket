package com.busticket.booking.repository.user

import com.busticket.booking.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Int>, JpaSpecificationExecutor<User> {
    fun findMemberByEmail(email: String): Optional<User>

    @Query("select m from User m LEFT JOIN FETCH m.policy p LEFT JOIN FETCH p.roles where m.email = :email")
    fun findMemberByEmailAndFetchPolicy(@Param("email") email: String): User

    @Query("select m from User m LEFT JOIN FETCH m.policy p LEFT JOIN FETCH p.roles where m.id = :id")
    fun findMemberByIdAndFetchPolicy(@Param("id") id: Int): Optional<User>
}