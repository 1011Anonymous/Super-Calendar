package com.example.supercalendar.domain.model.forecast_hourly

data class Hourly(
    val cloud: String? = null,
    val dew: String? = null,
    val fxTime: String? = null,
    val humidity: String? = null,
    val icon: String? = null,
    val pop: String? = null,
    val precip: String? = null,
    val pressure: String? = null,
    val temp: String? = null,
    val text: String? = null,
    val wind360: String? = null,
    val windDir: String? = null,
    val windScale: String? = null,
    val windSpeed: String? = null
)
