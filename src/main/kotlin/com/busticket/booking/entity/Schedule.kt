package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "schedules")
data class Schedule(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var startTime: Long? = null,
        var endTime: Long? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "schedule_template_id")
        var scheduleTemplate: ScheduleTemplate? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vehicle_category_id")
        var vehicleCategory: VehicleCategory? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "voyage_id")
        var voyage: Voyage? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "driver_id")
        var driver: User? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vehicle_id")
        var vehicle: Vehicle? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_by_id")
        var createdBy: User? = null,


        @Column(name = "schedule_template_id", insertable = false, updatable = false)
        var scheduleTemplateId: Int? = null,
        @Column(name = "vehicle_category_id", insertable = false, updatable = false)
        var vehicleCategoryId: Int? = null,
        @Column(name = "voyage_id", insertable = false, updatable = false)
        var voyageId: Int? = null,
        @Column(name = "driver_id", insertable = false, updatable = false)
        var driverId: Int? = null,
        @Column(name = "vehicle_id", insertable = false, updatable = false)
        var vehicleId: Int? = null,
        @Column(name = "created_by_id", insertable = false, updatable = false)
        var createdById: Int? = null,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value

) {
}