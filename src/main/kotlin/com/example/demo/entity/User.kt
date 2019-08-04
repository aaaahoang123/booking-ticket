package com.example.demo.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(name = "email_unique_index", columnNames = ["email"])])
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        var email: String? = null,
        var password: String? = null,
        var name: String? = null,
        var createdAt: Long = Calendar.getInstance().timeInMillis,
        var updatedAt: Long = Calendar.getInstance().timeInMillis,
        @Column(columnDefinition = "tinyint(4) not null default 1")
        var status: Int = 1,
        @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
        var products: Set<Product>? = null
)