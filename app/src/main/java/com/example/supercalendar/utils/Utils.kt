package com.example.supercalendar.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import kotlinx.coroutines.flow.filterNotNull
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

fun YearMonth.displayText(): String = "${this.month.displayText()} ${this.year}"

fun Month.displayText(): String = getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE)

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}

fun convertLocalDateToHoliday1(date: LocalDate): String = "${date.monthValue}月${date.dayOfMonth}号"

fun convertLocalDateToHoliday2(date: LocalDate): String = "${date.monthValue}月${date.dayOfMonth}日"

fun removeFromName(name: String): String {
    return if (name.startsWith("中国") || name.startsWith("国际")) {
        name.substring(2).trim()
    } else {
        name
    }
}

fun getWeekOfYear(date: LocalDate, startDayOfWeek: DayOfWeek): Int {
    val weekFields = WeekFields.of(
        startDayOfWeek,
        3
    )
    return if (date.get(weekFields.weekOfYear()) == 53) 1 else date.get(weekFields.weekOfYear())
}
