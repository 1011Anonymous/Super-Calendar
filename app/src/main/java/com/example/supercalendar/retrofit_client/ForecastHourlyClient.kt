package com.example.supercalendar.retrofit_client

import com.example.supercalendar.constant.Const.Companion.WEATHER_BASE_URL
import com.example.supercalendar.network.forecast_hourly.ForecastHourlyApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForecastHourlyClient {
    companion object {
        private var apiService: ForecastHourlyApiService? = null
        fun getInstance(): ForecastHourlyApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ForecastHourlyApiService::class.java)
            }
            return apiService!!
        }
    }
}