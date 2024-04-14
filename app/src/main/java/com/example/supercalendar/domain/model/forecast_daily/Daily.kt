package com.example.supercalendar.domain.model.forecast_daily

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("cloud")
    val cloud: String? = null,
    @SerializedName("fxDate")
    val fxDate: String? = null,
    @SerializedName("humidity")
    val humidity: String? = null,
    @SerializedName("iconDay")
    val iconDay: String? = null,
    @SerializedName("iconNight")
    val iconNight: String? = null,
    @SerializedName("moonPhase")
    val moonPhase: String? = null,
    @SerializedName("moonPhaseIcon")
    val moonPhaseIcon: String? = null,
    @SerializedName("moonrise")
    val moonrise: String? = null,
    @SerializedName("moonset")
    val moonset: String? = null,
    @SerializedName("precip")
    val precip: String? = null,
    @SerializedName("pressure")
    val pressure: String? = null,
    @SerializedName("sunrise")
    val sunrise: String? = null,
    @SerializedName("sunset")
    val sunset: String? = null,
    @SerializedName("tempMax")
    val tempMax: String? = null,
    @SerializedName("tempMin")
    val tempMin: String? = null,
    @SerializedName("textDay")
    val textDay: String? = null,
    @SerializedName("textNight")
    val textNight: String? = null,
    @SerializedName("uvIndex")
    val uvIndex: String? = null,
    @SerializedName("vis")
    val vis: String? = null,
    @SerializedName("wind360Day")
    val wind360Day: String? = null,
    @SerializedName("wind360Night")
    val wind360Night: String? = null,
    @SerializedName("windDirDay")
    val windDirDay: String? = null,
    @SerializedName("windDirNight")
    val windDirNight: String? = null,
    @SerializedName("windScaleDay")
    val windScaleDay: String? = null,
    @SerializedName("windScaleNight")
    val windScaleNight: String? = null,
    @SerializedName("windSpeedDay")
    val windSpeedDay: String? = null,
    @SerializedName("windSpeedNight")
    val windSpeedNight: String? = null
)
