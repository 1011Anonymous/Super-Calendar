package com.example.supercalendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.time.YearMonth

class HomeViewModel: ViewModel() {
    val visibleMonthState = mutableStateOf(YearMonth.now())
    val isGoBackToday = mutableStateOf(false)

    var hideWeather by mutableStateOf(false)
    var highlightWeekendsState by mutableStateOf(false)
    var firstDayOfWeek by mutableStateOf(DayOfWeek.MONDAY)


    fun setVisibleMonth(yearMonth: YearMonth) {
        visibleMonthState.value = yearMonth
    }

    fun resetToCurrentMonth() {
        visibleMonthState.value = YearMonth.now()
    }

    fun setIsGoBackToday(bool: Boolean) {
        isGoBackToday.value = bool
    }

    fun updateHideWeather() {
        hideWeather = !hideWeather
    }

    fun updateHighlight() {
        highlightWeekendsState = !highlightWeekendsState
    }

    fun updateFirstDayOfWeek(dayOfWeek: String) {
        firstDayOfWeek = when(dayOfWeek) {
            "周六" -> DayOfWeek.SATURDAY
            "周日" -> DayOfWeek.SUNDAY
            "周一" -> DayOfWeek.MONDAY
            else -> DayOfWeek.MONDAY
        }
    }

}