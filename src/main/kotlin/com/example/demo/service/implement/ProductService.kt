package com.example.demo.service.implement

import com.example.demo.dto.req.ProductReqDto
import com.example.demo.entity.Product
import com.example.demo.entity.User
import com.example.demo.repository.product.ProductRepository
import com.example.demo.repository.product.searchProducts
import com.example.demo.repository.relation
import com.example.demo.repository.user.UserRepository
import com.example.demo.service.contract.IProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class ProductService(
        @Autowired val userRepository: UserRepository,
        @Autowired val productRepository: ProductRepository
): IProductService {
    override fun createProduct(dto: ProductReqDto): Product {
        val user = userRepository.findById(dto.sellerId).get()
        val product = Product(
                name = dto.name,
                price = dto.price,
                seller = user
        )

        return productRepository.save(product)
    }

    override fun searchProduct(search: String): Page<Product> {
       return productRepository.findAll(
               Specification.where(searchProducts(search)
                       .and(relation<Product>(listOf("seller")))), PageRequest.of(0, 20)
       )
    }
}