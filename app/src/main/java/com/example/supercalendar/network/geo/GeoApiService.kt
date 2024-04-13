package com.example.supercalendar.network.geo

import com.example.supercalendar.constant.Const.Companion.WEATHER_API_KEY
import com.example.supercalendar.domain.model.geo.GeoInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApiService {
    @GET("/v2/city/lookup")
    suspend fun getLocation(
        @Query("location") location: String,
        @Query("key") key: String = WEATHER_API_KEY,
    ): GeoInfo
}