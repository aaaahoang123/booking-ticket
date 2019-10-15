package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.Voyage
import java.util.*

interface VoyageService: IBaseService<Voyage, Int> {
    fun getById(id: Int): Optional<Voyage>
}