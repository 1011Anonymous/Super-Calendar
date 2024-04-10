package com.example.supercalendar.domain.model.holiday

data class HolidayInfo(
    val code: Int,
    val msg: String,
    val result: List<Holiday>
)