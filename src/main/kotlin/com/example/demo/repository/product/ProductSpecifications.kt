package com.example.demo.repository.product

import com.example.demo.entity.Product
import com.example.demo.entity.User
import org.springframework.data.jpa.domain.Specification

fun searchProducts(search: String): Specification<Product> {
    return Specification { product, query, criteriaBuilder ->
        val sellerSubQuery = query.subquery(User::class.java)
        val seller = sellerSubQuery.from(User::class.java)
        val trueSearch = "%$search%"
        sellerSubQuery.select(seller)
                .where(
                        criteriaBuilder.or(
                                criteriaBuilder.like(seller["name"], trueSearch),
                                criteriaBuilder.like(seller["email"], trueSearch)
                        ),
                        criteriaBuilder.isMember(product, seller["products"])
                )
        criteriaBuilder.or(
                criteriaBuilder.like(product["name"], trueSearch),
                criteriaBuilder.exists(sellerSubQuery)
        )
    }
}