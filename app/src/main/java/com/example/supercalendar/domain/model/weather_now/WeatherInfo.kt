package com.example.supercalendar.domain.model.weather_now

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("fxLink")
    val fxLink: String? = null,
    @SerializedName("now")
    val now: Now? = Now(),
    @SerializedName("refer")
    val refer: Refer? = Refer(),
    @SerializedName("updateTime")
    val updateTime: String? = null
)
