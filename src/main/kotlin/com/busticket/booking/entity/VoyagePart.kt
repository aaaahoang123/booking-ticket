package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
class VoyagePart(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @ManyToOne
        @JoinColumn(name = "fromId", columnDefinition = "int default 0 not null")
        var from: Street? = null,
        @ManyToOne
        @JoinColumn(name = "toId", columnDefinition = "int default 0 not null")
        var to: Street? = null,
        var distance: Int = 0,
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "voyage_id")
        var voyage: Voyage? = null,
        var orderNumber: Int = 0,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),
        var status: Int = CommonStatus.ACTIVE.value
) {
}