package com.busticket.booking.repository.member

import com.busticket.booking.entity.Member
import org.springframework.data.jpa.domain.Specification

fun memberEmailEqual(email: String): Specification<Member> {
    return Specification { root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<String>("email"), email)
    }
}