package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.dto.StatisticDto
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("$API_PREFIX/statistics")
class StatisticController @Autowired constructor(
        private val orderService: OrderService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @GetMapping
    fun statistic(@RequestParam("from") from: Long? = null,
                  @RequestParam("to") to: Long? = null): ResponseEntity<Any> {
        val c = Calendar.getInstance()
        c.set(Calendar.DAY_OF_MONTH, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        val realFrom = from ?: c.timeInMillis
        val result = orderService.statistics(realFrom, to)
//        val result = StatisticDto(
//                orderStatistics = ordersStatistic
//        )
        return restResponse.restSuccess(result)
    }
}