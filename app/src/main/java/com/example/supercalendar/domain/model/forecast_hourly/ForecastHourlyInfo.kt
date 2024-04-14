package com.example.supercalendar.domain.model.forecast_hourly

data class ForecastHourlyInfo(
    val code: String? = null,
    val fxLink: String? = null,
    val hourly: List<Hourly>? = listOf(),
    val refer: Refer? = Refer(),
    val updateTime: String? = null
)
