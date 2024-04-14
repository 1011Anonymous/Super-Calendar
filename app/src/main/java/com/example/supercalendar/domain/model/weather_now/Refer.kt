package com.example.supercalendar.domain.model.weather_now

import com.google.gson.annotations.SerializedName

data class Refer(
    @SerializedName("license")
    val license: ArrayList<String>? = arrayListOf(),
    @SerializedName("sources")
    val sources: ArrayList<String>? = arrayListOf()
)
