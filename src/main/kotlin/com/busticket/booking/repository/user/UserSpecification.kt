package com.busticket.booking.repository.user

import com.busticket.booking.entity.User
import org.springframework.data.jpa.domain.Specification

fun userEmailEqual(email: String): Specification<User> {
    return Specification { root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<String>("email"), email)
    }
}