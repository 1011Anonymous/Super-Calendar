package com.example.supercalendar.network.weather_now

import com.example.supercalendar.constant.Const.Companion.WEATHER_API_KEY
import com.example.supercalendar.domain.model.weather_now.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherNowApiService {

    @GET("/v7/weather/now")
    suspend fun getWeatherNow(
        @Query("location") location: String,
        @Query("key") key: String = WEATHER_API_KEY
    ): WeatherInfo

}