package com.example.supercalendar.domain.model.geo

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("adm1") var adm1: String? = null,
    @SerializedName("adm2") var adm2: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("fxLink") var fxLink: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("isDst") var isDst: String? = null,
    @SerializedName("lat") var lat: String? = null,
    @SerializedName("lon") var lon: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("rank") var rank: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("tz") var tz: String? = null,
    @SerializedName("utcOffset") var utcOffset: String? = null
)
