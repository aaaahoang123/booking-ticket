package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "vehicle_categories")
class VehicleCategory(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var name: String = "",
        @Column(name = "seat_quantity")
        var seatQuantity: Int = 1,
        var price: Int = 0,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value,
        @OneToMany(mappedBy = "vehicleCategory", cascade = [CascadeType.ALL])
        var vehicles: Set<Vehicle> = setOf()
) {
}