package com.busticket.booking.dto

class UserDto(
        val id: Int,
        val email: String,
        var name: String? = null,
        var phoneNumber: String? = null,
        var address: String? = null,
        var avatar: String? = null,
        var birthday: Long? = null,
        var gender: Int? = null,
        var status: Int,
        var statusTitle: String,
        var createdAt: Long,
        var updatedAt: Long,
        var createdAtStr: String,
        var updatedAtStr: String,
        var accessToken: String? = null
)