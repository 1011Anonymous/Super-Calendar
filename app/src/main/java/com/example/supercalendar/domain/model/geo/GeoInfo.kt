package com.example.supercalendar.domain.model.geo

import com.google.gson.annotations.SerializedName

data class GeoInfo(
    @SerializedName("code")
    var code: String? = null,
    @SerializedName("location")
    var location: ArrayList<Location>? = arrayListOf(),
    @SerializedName("refer")
    var refer: Refer? = Refer()
)
