package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "voyages")
data class Voyage(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var name: String = "",
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value
) {
        @OneToMany(mappedBy = "voyage", cascade = [CascadeType.ALL])
        @OrderBy("orderNumber asc")
        var voyageParts: Set<VoyagePart>? = mutableSetOf()
        @ManyToMany(mappedBy = "voyages", fetch = FetchType.LAZY)
        var scheduleTemplates: Set<ScheduleTemplate> = mutableSetOf()
        @OneToMany(mappedBy = "voyage")
        var schedules: Set<Schedule>? = mutableSetOf()
}