package com.example.supercalendar.utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class TimeUtils {
    companion object {
        fun convertMillisToLocalTime(
            timeInMillis: Long,
            zoneId: ZoneId = ZoneId.systemDefault()
        ): LocalTime {
            val instant = Instant.ofEpochMilli(timeInMillis)
            val zonedDateTime = instant.atZone(zoneId)
            return zonedDateTime.toLocalTime()
        }

        fun convertLocalTimeToString(time: LocalTime): String {
            return DateTimeFormatter
                .ofPattern("HH:mm", Locale.getDefault())
                .format(time)
        }

        fun subtractTimeFromDateTime(
            dateTime: LocalDateTime,
            minutesToSubtract: Long,
            hoursToSubtract: Long
        ): LocalDateTime {
            // Create a Duration object comprising the minutes and hours to subtract
            val duration = Duration.ofMinutes(minutesToSubtract).plusHours(hoursToSubtract)

            // Subtract the duration from the dateTime
            return dateTime.minus(duration)
        }
    }
}

