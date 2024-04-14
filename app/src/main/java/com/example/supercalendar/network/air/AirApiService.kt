package com.example.supercalendar.network.air

import com.example.supercalendar.constant.Const.Companion.WEATHER_API_KEY
import com.example.supercalendar.domain.model.air.AirInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface AirApiService {
    @GET("/v7/air/now")
    suspend fun getAir(
        @Query("location") location: String,
        @Query("key") key:String = WEATHER_API_KEY
    ): AirInfo
}