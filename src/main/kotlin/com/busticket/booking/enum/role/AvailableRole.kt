package com.busticket.booking.enum.role

const val ADMIN_SPECIAL_ROLE = 100

const val ROLE_MANAGER_VOYAGE = "ROLE_MANAGER_VOYAGE"

fun getRoleName(role: String?): String {
    return when(role) {
        ROLE_MANAGER_VOYAGE -> "Quản lý tuyến đường"
        else -> "Không có quyền hạn"
    }
}