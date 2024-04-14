package com.example.supercalendar.retrofit_client

import com.example.supercalendar.constant.Const.Companion.WEATHER_BASE_URL
import com.example.supercalendar.network.weather_now.WeatherNowApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherNowClient {
    companion object {
        private var apiService: WeatherNowApiService? = null
        fun getInstance(): WeatherNowApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherNowApiService::class.java)
            }
            return apiService!!
        }
    }
}