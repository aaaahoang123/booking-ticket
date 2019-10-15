package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.User
import com.busticket.booking.entity.UserPolicy
import com.busticket.booking.entity.UserRole

interface PolicyService: IBaseService<UserPolicy, Int> {
    fun listRoles(): List<UserRole>
    fun userJoinPolicy(userID: Int, policyId: Int): User
    fun userOutPolicy(userID: Int): User
}