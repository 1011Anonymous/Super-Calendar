package com.example.supercalendar.retrofit_client

import com.example.supercalendar.constant.Const.Companion.WEATHER_API_KEY
import com.example.supercalendar.network.geo.GeoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GeoRetrofitClient {
    companion object {
        private var apiService: GeoApiService? = null
        fun getInstance(): GeoApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(WEATHER_API_KEY)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GeoApiService::class.java)
            }
            return apiService!!
        }
    }
}