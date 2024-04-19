package com.example.supercalendar.utils

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

fun convertMillisToLocalTime(timeInMillis: Long, zoneId: ZoneId = ZoneId.systemDefault()): LocalTime {
    val instant = Instant.ofEpochMilli(timeInMillis)
    val zonedDateTime = instant.atZone(zoneId)
    return zonedDateTime.toLocalTime()
}
