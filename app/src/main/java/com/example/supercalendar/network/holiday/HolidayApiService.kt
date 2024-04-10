package com.example.supercalendar.network.holiday

import com.example.supercalendar.constant.Const
import com.example.supercalendar.domain.model.holiday.HolidayInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayApiService {
    @GET("/jiejiari/index")
    suspend fun getHolidayInfo(
        @Query("key") key: String = Const.HOLIDAY_API_KEY,
        @Query("date") date: String,
        @Query("type") type: Int = 2
    ): HolidayInfo
}