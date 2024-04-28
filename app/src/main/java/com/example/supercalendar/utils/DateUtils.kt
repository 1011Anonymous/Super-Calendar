package com.example.supercalendar.utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs


class DateUtils {
    companion object {

        fun convertMillisToLocalDate(millis: Long): LocalDate {
            return Instant
                .ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }

        private fun convertMillisToLocalDateWithFormatter(
            date: LocalDate,
            dateTimeFormatter: DateTimeFormatter
        ): LocalDate {
            //Convert the date to a long in millis using a dateformmater
            val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            //Convert the millis to a localDate object
            return Instant
                .ofEpochMilli(dateInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }


        fun dateToString(date: LocalDate): String {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy年M月d日EE", Locale.getDefault())
            val dateInMillis = convertMillisToLocalDateWithFormatter(date, dateFormatter)
            return dateFormatter.format(dateInMillis)
        }

        fun dateToStringISO(date: LocalDate): String {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
            val dateInMillis = convertMillisToLocalDateWithFormatter(date, dateFormatter)
            return dateFormatter.format(dateInMillis)
        }

        fun calculateDaysBetween(date: LocalDate): Int {
            val today = LocalDate.now()

            // Ensure dates are in the same year
            if (date.year != today.year) {
                throw IllegalArgumentException("Dates must be in the same year")
            }

            val period = Period.between(date, today)
            return period.days
        }

        fun calculateDaysUntilDate(targetDate: LocalDate): Long {
            val today = LocalDate.now()

            return if (targetDate.isBefore(today)) {
                // Target date is in the past this year; calculate days until next year
                val nextYearDate = targetDate.withYear(today.year + 1)
                val duration = Duration.between(today.atStartOfDay(), nextYearDate.atStartOfDay())
                duration.toDays()
            } else {
                // Target date is this year or in the future; calculate days directly
                val duration = Duration.between(today.atStartOfDay(), targetDate.atStartOfDay())
                duration.toDays()
            }
        }

    }
}