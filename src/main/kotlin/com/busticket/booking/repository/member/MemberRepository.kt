package com.busticket.booking.repository.member

import com.busticket.booking.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository: JpaRepository<Member, Int>, JpaSpecificationExecutor<Member> {
    fun findMemberByEmail(email: String): Optional<Member>

    @Query("select m from Member m LEFT JOIN FETCH m.policy p LEFT JOIN FETCH p.roles where m.email = :email")
    fun findMemberByEmailAndFetchPolicy(@Param("email") email: String): Member

    @Query("select m from Member m LEFT JOIN FETCH m.policy p LEFT JOIN FETCH p.roles where m.id = :id")
    fun findMemberByIdAndFetchPolicy(@Param("id") id: Int): Optional<Member>
}