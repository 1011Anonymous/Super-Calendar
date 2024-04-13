package com.example.supercalendar.domain.model.geo

import com.google.gson.annotations.SerializedName

data class Refer(
    @SerializedName("license")
    var license: ArrayList<String>? = arrayListOf(),
    @SerializedName("sources")
    var sources: ArrayList<String>? = arrayListOf()
)
