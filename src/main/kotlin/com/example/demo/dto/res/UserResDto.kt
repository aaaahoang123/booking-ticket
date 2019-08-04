package com.example.demo.dto.res

import com.example.demo.entity.User
import org.hibernate.Hibernate
import kotlin.streams.toList

class UserResDto(user: User) {
    val id = user.id
    val email = user.email
    val name = user.name
    val products = if (Hibernate.isInitialized(user.products)) user.products?.stream()?.map { product -> ProductResDto(product) }?.toList() else listOf()
}