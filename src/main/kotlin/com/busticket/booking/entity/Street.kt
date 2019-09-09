package com.busticket.booking.entity

import javax.persistence.*

@Entity
@Table(name = "street")
class Street(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @Column(nullable = false, name = "_name")
        var name: String = "",
        @Column(name = "_prefix")
        var prefix: String = "",
        @ManyToOne
        @JoinColumn(name = "_district_id")
        var district: District? = null,
        @ManyToOne
        @JoinColumn(name = "_province_id")
        var province: Province? = null,
        var tag: String = ""
) {

}