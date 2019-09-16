package com.busticket.booking.repository

import org.springframework.data.jpa.domain.Specification

const val ACTIVE_STATUS = 1
const val INACTIVE_STATUS = -1

fun <T> initSpec(): Specification<T> {
    return Specification { _, _, _ -> null }
}

fun <T> hasStatus(activeStatus: Int = ACTIVE_STATUS): Specification<T> {
    return Specification { root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("status"), activeStatus)
    }
}

fun <Root, Relation> fetchRelation(relationName: String): Specification<Root> {
    return Specification { root, query, criteriaBuilder ->
        query.distinct(true)
        root.fetch<Root, Relation>(relationName)
        criteriaBuilder.conjunction()
    }
}

fun <Root> fetchRelations(relationNames: List<String>): Specification<Root> {
    return Specification { root, query, criteriaBuilder ->
        query.distinct(true)
        for (relation in relationNames) {
            root.fetch<Root, Any>(relation)
        }
        criteriaBuilder.conjunction()
    }
}

fun <Root> distinctRootEntity(): Specification<Root> {
    return Specification { _, query, _ ->
        query.distinct(true)
        null
    }
}
