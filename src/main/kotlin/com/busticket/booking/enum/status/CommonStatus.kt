package com.busticket.booking.enum.status

enum class CommonStatus(val value: Int, val title: String) {
    ACTIVE(1, "active"), INACTIVE(-1, "inactive")
}

fun commonStatusTitle(value: Int): String {
    return when (value) {
        CommonStatus.ACTIVE.value -> CommonStatus.ACTIVE.title
        CommonStatus.INACTIVE.value -> CommonStatus.INACTIVE.title
        else -> ""
    }
}