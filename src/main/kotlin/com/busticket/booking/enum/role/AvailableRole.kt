package com.busticket.booking.enum.role

const val ADMIN_SPECIAL_ROLE = 100

const val ROLE_MANAGER_VOYAGE = "ROLE_MANAGER_VOYAGE"
const val ROLE_MANAGER_VEHICLE = "ROLE_MANAGER_VEHICLE"
const val ROLE_MANAGER_VEHICLE_CATEGORY = "ROLE_MANAGER_VEHICLE_CATEGORY"
const val ROLE_MANAGER_SCHEDULE_TEMPLATE = "ROLE_MANAGER_SCHEDULE_TEMPLATE"
const val ROLE_MANAGER_SCHEDULE = "ROLE_MANAGER_SCHEDULE"
const val ROLE_MANAGER_ORDER = "ROLE_MANAGER_ORDER"
const val ROLE_MANAGER_CUSTOMER_TYPE = "ROLE_MANAGER_CUSTOMER_TYPE"
const val ROLE_MANAGER_USER = "ROLE_MANAGER_USER"
const val ROLE_MANAGER_POLICY = "ROLE_MANAGER_POLICY"

fun getRoleName(role: String?): String {
    return when(role) {
        ROLE_MANAGER_VOYAGE -> "Quản lý tuyến đường"
        ROLE_MANAGER_VEHICLE -> "Quản lý xe"
        ROLE_MANAGER_VEHICLE_CATEGORY -> "Quản lý nhóm xe"
        ROLE_MANAGER_SCHEDULE_TEMPLATE -> "Quản lý mẫu lịch"
        ROLE_MANAGER_SCHEDULE -> "Quản lý lịch"
        ROLE_MANAGER_ORDER -> "Quản lý chuyến cho khách"
        ROLE_MANAGER_CUSTOMER_TYPE -> "Quản lý nhóm khách"
        ROLE_MANAGER_USER -> "Quản lý nhân viên"
        ROLE_MANAGER_POLICY -> "Quản lý quyền hạn"
        else -> "Không có quyền hạn"
    }
}