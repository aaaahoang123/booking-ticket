package com.busticket.booking.lib.datetime

import com.busticket.booking.lib.exception.ExecuteException
import java.text.SimpleDateFormat
import java.util.*

fun <T> parse(input: T, format: String = "yyyy-M-dd hh:mm:ss"): Date {
    if (input is Number) {
        return Date(input.toLong())
    } else if (input is String) {
        val sdf = SimpleDateFormat(format)
        return sdf.parse(input)
    }
    throw ExecuteException("Date input not supported, parse method accept only Long type or String type input")
}

fun <T> format(input: T, format: String = "yyyy-M-dd hh:mm:ss"): String {
    val resource = when (input) {
        is Number -> parse(input)
        is Date -> input
        else -> throw ExecuteException("Date input not supported, format method accept only Long type or Date type input")
    }
    return SimpleDateFormat(format).format(resource)
}