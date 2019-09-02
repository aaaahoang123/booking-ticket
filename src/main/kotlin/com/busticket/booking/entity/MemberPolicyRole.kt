package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "policy_roles")
class MemberPolicyRole(
        @Id
        @Column(columnDefinition = "varchar(191)")
        var id: String = "",
        @Column(nullable = false)
        var name: String = "",
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "roles")
        var policies: Set<MemberPolicy> = emptySet()
)