package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "schedule_template")
data class ScheduleTemplate(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var timeStart: Long? = null,
        var timeEnd: Long? = null,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        var status: Int = CommonStatus.ACTIVE.value

) {
        @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinTable(
                name = "schedule_template_voyage",
                joinColumns = [JoinColumn(name = "schedule_template_id")],
                inverseJoinColumns = [JoinColumn(name = "voyage_id")]
        )
        var voyages: Set<Voyage> = setOf()
}