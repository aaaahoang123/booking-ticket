package com.busticket.booking.dto

class StatisticDto(
        var orderStatistics: List<OrderStatisticDto> ? = mutableListOf(),
        var revenueStatistics: List<RevenueStatisticDto> ? = mutableListOf(),
        var customerRanking: List<CustomerRankingDto> ? = mutableListOf()
) {
}