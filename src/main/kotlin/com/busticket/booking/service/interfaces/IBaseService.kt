package com.busticket.booking.service.interfaces

import kotlin.reflect.KClass

interface IBaseService<T : Any, ID> {
    fun getInstanceClass(): KClass<T>
    fun create(dto: Any): T
    fun findAll(): List<T>
    fun findAllActiveItems(): List<T>
    fun singleById(id: ID): T
    fun edit(id: ID, dto: Any): T
    fun delete(id: ID): T
}