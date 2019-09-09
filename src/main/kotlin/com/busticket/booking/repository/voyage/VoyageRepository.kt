package com.busticket.booking.repository.voyage

import com.busticket.booking.entity.Voyage
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface VoyageRepository: BaseRepository<Voyage, Int> {
}