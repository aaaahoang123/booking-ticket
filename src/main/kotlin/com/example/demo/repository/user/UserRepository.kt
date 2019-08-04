package com.example.demo.repository.user

import com.example.demo.entity.User
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Int>, JpaSpecificationExecutor<User> {
    fun findUserByEmail(email: String): Optional<User>
}