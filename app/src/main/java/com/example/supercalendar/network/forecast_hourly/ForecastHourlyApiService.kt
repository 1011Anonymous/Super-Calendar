package com.example.supercalendar.network.forecast_hourly

import com.example.supercalendar.constant.Const.Companion.WEATHER_API_KEY
import com.example.supercalendar.domain.model.forecast_hourly.ForecastHourlyInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastHourlyApiService {
    @GET("/v7/weather/24h")
    suspend fun getForecastHourly(
        @Query("location") location: String,
        @Query("key") key:String = WEATHER_API_KEY
    ): ForecastHourlyInfo
}