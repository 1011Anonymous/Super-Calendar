package com.example.supercalendar.domain.model.holiday

data class HolidayInfoResponse(
    val code: Int,
    val msg: String,
    val result: HolidayResult
)