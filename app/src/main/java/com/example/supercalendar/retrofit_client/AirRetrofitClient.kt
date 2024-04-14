package com.example.supercalendar.retrofit_client

import com.example.supercalendar.constant.Const.Companion.WEATHER_BASE_URL
import com.example.supercalendar.network.air.AirApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirRetrofitClient {
    companion object {
        private var apiService: AirApiService? = null
        fun getInstance(): AirApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(AirApiService::class.java)
            }
            return apiService!!
        }
    }
}