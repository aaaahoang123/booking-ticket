package com.busticket.booking.entity

import javax.persistence.*

@Entity
@Table(name = "district")
class District(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @Column(nullable = false, name = "_name")
        var name: String = "",
        @Column(name = "_prefix")
        var prefix: String = "",
        @ManyToOne
        @JoinColumn(name = "_province_id")
        var province: Province? = null,
        @OneToMany(mappedBy = "district")
        var street: Set<Street> = setOf()
) {
}