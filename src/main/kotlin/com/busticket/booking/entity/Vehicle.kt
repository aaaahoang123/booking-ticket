package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "vehicle")
class Vehicle(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var name: String = "",
        var plate: String = "",
        var color: String = "",
        @ManyToOne
        @JoinColumn(name = "category_id")
        var vehicleCategory: VehicleCategory? = null,
        @Column(name = "category_id", insertable = false, updatable = false)
        var vehicleCategoryId: Int? = null,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value
) {
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    var schedules: Set<Schedule>? = mutableSetOf()
}