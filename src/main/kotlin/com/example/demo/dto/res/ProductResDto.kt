package com.example.demo.dto.res

import com.example.demo.entity.Product
import org.hibernate.Hibernate

class ProductResDto(product: Product) {
    val id = product.id
    val name = product.name
    val price = product.price
    val seller = if (Hibernate.isInitialized(product.seller)) UserResDto(product.seller!!) else null
}