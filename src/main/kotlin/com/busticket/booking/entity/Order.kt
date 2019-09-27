package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id")
        var customer: Customer? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "schedule_id")
        var schedule: Schedule? = null,
        var finalPrice: Double? = null,
        var paidStatus: Int? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_by")
        var createdBy: User? = null,

        @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
        var orderDetail: Set<OrderDetail> = mutableSetOf(),

        @Column(name = "customer_id", insertable = false, updatable = false)
        var customerId: Int? = null,
        @Column(name = "schedule_id", insertable = false, updatable = false)
        var scheduleId: Int? = null,
        @Column(name = "created_by", insertable = false, updatable = false)
        var createdById: Int? = null,

        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value
) {
}