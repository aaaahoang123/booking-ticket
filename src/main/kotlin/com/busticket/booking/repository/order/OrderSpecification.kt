package com.busticket.booking.repository.order

import com.busticket.booking.entity.Order
import org.springframework.data.jpa.domain.Specification

fun orderByCustomer(customerId: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("customerId"), customerId)
    }
}

fun orderCreatedBy(createdById: Int): Specification<Order> {
    return Specification {root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("createdById"), createdById)
    }
}

fun orderOfSchedule(scheduleId: Int): Specification<Order> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("scheduleId"), scheduleId)
    }
}

fun paidStatus(paidStatus: Int): Specification<Order> {
    return Specification {root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("paidStatus"), paidStatus)
    }
}

fun orderStatus(status: Int): Specification<Order> {
    return Specification {root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("status"), status)
    }
}
