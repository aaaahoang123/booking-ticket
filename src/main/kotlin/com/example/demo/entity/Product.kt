package com.example.demo.entity

import java.util.*
import javax.persistence.*

@Entity
class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var name: String? = null,
        var price: Long? = null,
        var createdAt: Long = Calendar.getInstance().timeInMillis,
        var updatedAt: Long = Calendar.getInstance().timeInMillis,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "sellerId", referencedColumnName = "id")
        var seller: User? = null,
        @Column(columnDefinition = "int not null default 1")
        var status: Int = 1
)