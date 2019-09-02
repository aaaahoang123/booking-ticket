package com.busticket.booking.enum.status

enum class CommonStatus(val value: Int, title: String) {
    ACTIVE(1, "active"), INACTIVE(-1, "inactive")
}