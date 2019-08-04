package com.example.demo.controller

import com.example.demo.dto.req.ProductReqDto
import com.example.demo.dto.res.ProductResDto
import com.example.demo.entity.Product
import com.example.demo.service.contract.IProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import kotlin.streams.toList

@RestController
@RequestMapping(value = ["/api/products"])
class ProductController(
        @Autowired private val productService: IProductService
) {
    @PostMapping(value = ["/create"])
    fun create(@RequestBody dto: ProductReqDto, error: Errors): Product {
//        if (error.hasErrors())
        return productService.createProduct(dto)
    }

    @GetMapping(value = ["/", ""])
    fun list(@RequestParam search: String?): List<ProductResDto> {
        val products: Page<Product> = productService.searchProduct(search as String)
        return products.content.stream().map { product -> ProductResDto(product) }.toList()
    }
}