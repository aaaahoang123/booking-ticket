package com.example.demo.repository

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaQuery

fun <T> initSpec(): Specification<T> {
    return Specification { _, _, _ -> null }
}

fun <Root, Relation> relation(relationName: String): Specification<Root> {
    return Specification { root, query, criteriaBuilder ->
        root.fetch<Root, Relation>(relationName)
        criteriaBuilder.conjunction()
    }
}

fun <Root> relation(relationNames: List<String>): Specification<Root> {
    return Specification { root, _, criteriaBuilder ->
        for (relation in relationNames) {
            root.fetch<Root, Any>(relation)
        }
        criteriaBuilder.conjunction()
    }
}