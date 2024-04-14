package com.example.supercalendar.network.forecast_daily

import com.example.supercalendar.constant.Const.Companion.WEATHER_API_KEY
import com.example.supercalendar.domain.model.forecast_daily.ForecastDailyInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastDailyApiService {
    @GET("/v7/weather/7d")
    suspend fun getForecastDaily(
        @Query("location") location: String,
        @Query("key") key:String = WEATHER_API_KEY
    ): ForecastDailyInfo
}