package com.busticket.booking.entity

import javax.persistence.*

@Entity
@Table(name = "province")
class Province(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @Column(nullable = false, name = "_name")
        var name: String = "",
        @Column(nullable = false, name = "_code")
        var code: String = "",

        @OneToMany(mappedBy = "province")
        var districts: Set<District> = setOf(),
        @OneToMany(mappedBy = "province")
        var streets: Set<Street> = setOf()

) {

}