package com.example.demo.service.contract

import com.example.demo.dto.req.ProductReqDto
import com.example.demo.entity.Product
import org.springframework.data.domain.Page

interface IProductService {
    fun createProduct(dto: ProductReqDto): Product
    fun searchProduct(search: String): Page<Product>
}