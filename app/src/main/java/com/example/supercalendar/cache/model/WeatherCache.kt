package com.example.supercalendar.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherCache(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val locationName: String,
    val currentTemp: String,
    val tempMin0: String,
    val tempMax0: String,
    val currentText: String,
    val aqi: String,
    val category: String,
    val currentWindDir: String,
    val currentWindScale: String,
    val currentHumidity: String,
    val text1: String,
    val text2: String,
    val windDir1: String,
    val windDir2: String,
    val windScale1: String,
    val windScale2: String,
    val tempMin1: String,
    val tempMin2: String,
    val tempMax1: String,
    val tempMax2: String,
    val icon1: String,
    val icon2: String,
    val currentIcon: String,
    val feelsLike: String,
    val pm25: String,
    val sunrise: String,
    val sunset: String,

)
