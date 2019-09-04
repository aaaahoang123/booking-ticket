package com.busticket.booking.service.impl

import com.busticket.booking.CLIENT_DATE_FORMAT
import com.busticket.booking.dto.UserDto
import com.busticket.booking.entity.User
import com.busticket.booking.enum.status.commonStatusTitle
import com.busticket.booking.lib.datetime.format
import com.busticket.booking.lib.locale.LocaleService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
@Scope("request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class DtoBuilderServiceImpl @Autowired constructor(
        private val localeService: LocaleService,
        private val req: HttpServletRequest
) : DtoBuilderService {
    override fun buildUserDto(user: User, token: String?): UserDto {
        return UserDto(
                id = user.id!!,
                email = user.email,
                name = user.name,
                address = user.address,
                avatar = user.avatar,
                birthday = user.birthday,
                gender = user.gender,
                phoneNumber = user.phoneNumber,
                status = user.status,
                statusTitle = localeService.getMessage(commonStatusTitle(user.status), req),
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                createdAtStr = format(user.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(user.updatedAt, CLIENT_DATE_FORMAT),
                accessToken = token
        )
    }
}