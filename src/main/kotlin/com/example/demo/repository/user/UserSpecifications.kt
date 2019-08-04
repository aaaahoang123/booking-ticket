package com.example.demo.repository.user

import com.example.demo.entity.User
import org.springframework.data.jpa.domain.Specification

fun userHasSearch(search: String): Specification<User> {
    return Specification{ root, query, cb ->
        cb.or(
                cb.like(root["name"], "%$search%"),
                cb.like(root["email"], "%$search%")
        )
    }
}

fun userHasStatus(status: Int): Specification<User> {
    return Specification{ root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("status"), status)
    }
}