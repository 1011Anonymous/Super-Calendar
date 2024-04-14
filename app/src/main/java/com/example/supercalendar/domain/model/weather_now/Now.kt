package com.example.supercalendar.domain.model.weather_now

import com.google.gson.annotations.SerializedName

data class Now(
    @SerializedName("cloud")
    val cloud: String? = null,
    @SerializedName("dew")
    val dew: String? = null,
    @SerializedName("feelsLike")
    val feelsLike: String? = null,
    @SerializedName("humidity")
    val humidity: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("obsTime")
    val obsTime: String? = null,
    @SerializedName("precip")
    val precip: String? = null,
    @SerializedName("pressure")
    val pressure: String? = null,
    @SerializedName("temp")
    val temp: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("vis")
    val vis: String? = null,
    @SerializedName("wind360")
    val wind360: String? = null,
    @SerializedName("windDir")
    val windDir: String? = null,
    @SerializedName("windScale")
    val windScale: String? = null,
    @SerializedName("windSpeed")
    val windSpeed: String? = null
)
