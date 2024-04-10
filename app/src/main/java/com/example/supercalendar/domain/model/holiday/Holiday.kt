package com.example.supercalendar.domain.model.holiday

data class Holiday(
    val cnweekday: String,
    val date: String,
    val daycode: Int,
    val end: Int,
    val enname: String,
    val holiday: String,
    val info: String,
    val isnotwork: Int,
    val lunarday: String,
    val lunarmonth: String,
    val lunaryear: String,
    val name: String,
    val now: Int,
    val remark: String,
    val rest: String,
    val start: Int,
    val tip: String,
    val vacation: List<String>,
    val wage: Int,
    val weekday: Int
)