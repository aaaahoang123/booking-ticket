package com.busticket.booking.repository.order

import com.busticket.booking.entity.Order
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import java.math.BigDecimal
import javax.persistence.criteria.Root
import javax.persistence.criteria.CriteriaBuilder


fun orderByCustomer(customerId: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("customerId"), customerId)
    }
}

fun orderCreatedBy(createdById: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("createdById"), createdById)
    }
}

fun orderOfSchedule(scheduleId: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("scheduleId"), scheduleId)
    }
}

fun paidStatus(paidStatus: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("paidStatus"), paidStatus)
    }
}

fun orderStatus(status: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("status"), status)
    }
}

fun statisticFrom(from: Long): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.quot(root.get("createdAt"), 1).`as`(Long::class.java), from)
    }
}

fun statisticTo(to: Long): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.quot(root.get("createdAt"), 1).`as`(Long::class.java), to)
    }
}