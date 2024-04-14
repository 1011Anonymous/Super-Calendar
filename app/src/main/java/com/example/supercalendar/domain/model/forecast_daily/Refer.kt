package com.example.supercalendar.domain.model.forecast_daily

import com.google.gson.annotations.SerializedName

data class Refer(
    @SerializedName("license")
    val license: ArrayList<String>? = arrayListOf(),
    @SerializedName("sources")
    val sources: ArrayList<String>? = arrayListOf()
)
