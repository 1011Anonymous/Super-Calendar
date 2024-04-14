package com.example.supercalendar.retrofit_client

import com.example.supercalendar.constant.Const.Companion.WEATHER_BASE_URL
import com.example.supercalendar.network.forecast_daily.ForecastDailyApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForecastDailyClient {
    companion object {
        private var apiService: ForecastDailyApiService? = null
        fun getInstance(): ForecastDailyApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ForecastDailyApiService::class.java)
            }
            return apiService!!
        }
    }
}