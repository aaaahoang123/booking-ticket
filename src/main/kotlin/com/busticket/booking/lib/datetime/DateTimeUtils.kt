package com.busticket.booking.lib.datetime

import com.busticket.booking.lib.exception.ExecuteException
import java.text.SimpleDateFormat
import java.util.*

fun parse(input: Long, format: String = "yyyy-M-dd hh:mm:ss"): Calendar {
    val cal = Calendar.getInstance()
    cal.time = Date(input)
    return cal
}

fun parse(input: String, format: String = "yyyy-M-dd hh:mm:ss"): Calendar {
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat(format)
    cal.time = sdf.parse(input)
    return cal
}

fun format(input: Long, format: String = "yyyy-M-dd hh:mm:ss"): String {

    return SimpleDateFormat(format).format(parse(input).time)
}

fun format(input: String, format: String = "yyyy-M-dd hh:mm:ss"): String {
    return SimpleDateFormat(format).format(parse(input))
}

fun format(input: Calendar, format: String = "yyyy-M-dd hh:mm:ss"): String {
    return SimpleDateFormat(format).format(input)
}

fun format(input: Date, format: String = "yyyy-M-dd hh:mm:ss"): String {
    return SimpleDateFormat(format).format(input)
}

fun startOfDay(date: Calendar): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = date.time
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    calendar.set(Calendar.AM_PM, 0)
    return calendar
}

fun startOfDay(date: Long): Calendar {

    return startOfDay(parse(date))
}

fun endOfDay(date: Calendar): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = date.time
    calendar.set(Calendar.HOUR, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar
}

fun endOfDay(date: Long): Calendar {
    return endOfDay(parse(date))
}