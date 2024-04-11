package com.example.supercalendar.network.holiday

import com.example.supercalendar.domain.model.holiday.Holiday
import kotlinx.coroutines.flow.Flow

interface HolidayRepository {
    suspend fun getHolidayList(date: String): Flow<Result<List<Holiday>>>

}