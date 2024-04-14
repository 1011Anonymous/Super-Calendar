package com.example.supercalendar.domain.model.forecast_daily

import com.google.gson.annotations.SerializedName

data class ForecastDailyInfo(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("daily")
    val daily: ArrayList<Daily>? = arrayListOf(),
    @SerializedName("fxLink")
    val fxLink: String? = null,
    @SerializedName("refer")
    val refer: Refer? = Refer(),
    @SerializedName("updateTime")
    val updateTime: String? = null
)
