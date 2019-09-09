package com.busticket.booking.service.impl

import com.busticket.booking.CLIENT_DATE_FORMAT
import com.busticket.booking.dto.UserDto
import com.busticket.booking.dto.VoyageDto
import com.busticket.booking.dto.VoyagePartDto
import com.busticket.booking.entity.User
import com.busticket.booking.entity.Voyage
import com.busticket.booking.entity.VoyagePart
import com.busticket.booking.enum.status.commonStatusTitle
import com.busticket.booking.lib.datetime.format
import com.busticket.booking.lib.locale.LocaleService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.hibernate.Hibernate
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

    override fun buildVoyageDto(voyage: Voyage): VoyageDto {
        val result = VoyageDto(
                id = voyage.id!!,
                name = voyage.name,
                createdAt = voyage.createdAt,
                updatedAt = voyage.updatedAt,
                status = voyage.status
        )
        if (Hibernate.isInitialized(voyage.voyageParts))
            result.voyageParts = voyage.voyageParts.map { buildVoyagePartDto(it) }
        return result
    }

    override fun buildVoyagePartDto(voyagePart: VoyagePart): VoyagePartDto {
        return VoyagePartDto(
                id = voyagePart.id!!,
                fromId = voyagePart.from?.id ?: 0,
                fromName = voyagePart.from?.name ?: "",
                toId = voyagePart.to?.id ?: 0,
                toName = voyagePart.to?.name ?: "",
                distance = voyagePart.distance,
                orderNumber = voyagePart.orderNumber,
                createdAt = voyagePart.createdAt,
                updatedAt = voyagePart.updatedAt,
                createdAtStr = format(voyagePart.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(voyagePart.updatedAt, CLIENT_DATE_FORMAT),
                status = voyagePart.status

        )
    }
}