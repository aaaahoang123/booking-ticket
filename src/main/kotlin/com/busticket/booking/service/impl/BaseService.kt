package com.busticket.booking.service.impl

import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.lib.assignObject
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.BaseRepository
import com.busticket.booking.repository.hasStatus
import com.busticket.booking.service.interfaces.IBaseService
import org.springframework.data.jpa.domain.Specification
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

abstract class BaseService<T : Any, IDType> : IBaseService<T, IDType> {
    protected lateinit var primaryRepo: BaseRepository<T, IDType>
    protected var ACTIVE_STATUS = CommonStatus.ACTIVE.value
    protected var INACTIVE_STATUS = CommonStatus.INACTIVE.value
    protected var STATUS_FIELD = "status"

    override fun create(dto: Any): T {
        val instance = assignObject(getInstanceClass().java.newInstance(), dto)
        return primaryRepo.save(instance)
    }

    override fun findAll(): List<T> {
        return primaryRepo.findAll()
    }

    override fun findAllActiveItems(): List<T> {
        return primaryRepo.findAll(Specification.where<T>(hasStatus(ACTIVE_STATUS)))
    }

    override fun singleById(id: IDType): T {
        val oResult = primaryRepo.findById(id)
        if (!oResult.isPresent)
            throw ExecuteException("not_found")
        return oResult.get()
    }

    override fun edit(id: IDType, dto: Any): T {
        return primaryRepo.save(assignObject(singleById(id), dto))
    }

    override fun delete(id: IDType): T {
        val instance = singleById(id)
        getInstanceClass().memberProperties
                .filterIsInstance<KMutableProperty<*>>()
                .forEach { prop ->
                    if (prop.name == STATUS_FIELD) {
                        prop.setter.call(instance, INACTIVE_STATUS)
                    }
                }
        return primaryRepo.save(instance)
    }
}