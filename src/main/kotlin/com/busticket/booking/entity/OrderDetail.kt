package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
class OrderDetail(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "order_id")
        var order: Order? = null,
        @ManyToOne
        @JoinColumn(name = "vehicle_category_id")
        var vehicleCategory: VehicleCategory? = null,
        @ManyToOne
        @JoinColumn(name = "customer_type_id")
        var customerType: CustomerType? = null,
        var unitPrice: Int? = null,
        var originToTalPrice: Double? = null,
        var totalPrice: Double? = null,
        var quantity: Int? = null,
        var discount: Int? = null,
//        var travelFromId: Int? = null,
//        var travelToId: Int? = null,
        @ManyToOne
        @JoinColumn(name = "travel_from_id")
        var travelFrom: VoyagePart? = null,
        @ManyToOne
        @JoinColumn(name = "travel_to_id")
        var travelTo: VoyagePart? = null,

        @Column(name = "order_id", insertable = false, updatable = false)
        var orderId: Int? = null,

        @Column(name = "vehicle_category_id", insertable = false, updatable = false)
        var vehicleCategoryId: Int? = null,

        @Column(name = "customer_type_id", insertable = false, updatable = false)
        var customerTypeId: Int? = null,

        @Column(name = "travel_from_id", insertable = false, updatable = false)
        var travelFromId: Int? = null,

        @Column(name = "travel_to_id", insertable = false, updatable = false)
        var travelToId: Int? = null,

        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value
) {
}