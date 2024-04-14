package com.example.supercalendar.domain.model.air

data class AirInfo(
    val code: String? = null,
    val fxLink: String? = null,
    val now: Now? = Now(),
    val refer: Refer? = Refer(),
    val station: ArrayList<Station>? = arrayListOf(),
    val updateTime: String? = null
)
