package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "members", uniqueConstraints = [UniqueConstraint(name = "email_unique", columnNames = ["email"])])
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @Column(length = 191, nullable = false)
        var email: String = "",
        var password: String? = null,
        var name: String? = null,
        var phoneNumber: String? = null,
        var address: String? = null,
        var avatar: String? = null,
        var birthday: Long? = null,
        var gender: Int? = null,
        var status: Int = CommonStatus.ACTIVE.value,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),

        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "policyId", columnDefinition = "int default 0")
        var policy: UserPolicy? = null
)