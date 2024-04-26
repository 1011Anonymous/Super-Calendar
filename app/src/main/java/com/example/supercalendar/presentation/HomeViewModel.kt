package com.example.supercalendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.datastore.StoreUserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userSettings: StoreUserSettings
) : ViewModel() {

    val visibleMonthState = mutableStateOf(YearMonth.now())
    val isGoBackToday = mutableStateOf(false)

    val darkTheme = userSettings.settingStatusFlow.map {
        it.isDarkTheme
    }

    val hideWeather = userSettings.settingStatusFlow.map {
        it.isHideWeather
    }
    //var hideWeather by mutableStateOf(false)

    val highlightWeekendsState = userSettings.settingStatusFlow.map {
        it.isHighlight
    }
    //var highlightWeekendsState by mutableStateOf(false)

    val firstDayOfWeek = userSettings.settingStatusFlow.map {
        when (it.firstDayOfWeek) {
            "周一" -> DayOfWeek.MONDAY
            "周六" -> DayOfWeek.SATURDAY
            "周日" -> DayOfWeek.SUNDAY
            else -> DayOfWeek.MONDAY
        }
    }
    //var firstDayOfWeek by mutableStateOf(DayOfWeek.MONDAY)

    val displayHoliday = userSettings.settingStatusFlow.map {
        it.isHoliday
    }
    //var displayHoliday by mutableStateOf(true)

    val displayLunar = userSettings.settingStatusFlow.map {
        it.isLunar
    }
    //var displayLunar by mutableStateOf(true)

    val displayFestival = userSettings.settingStatusFlow.map {
        it.isFestival
    }
    //var displayFestival by mutableStateOf(true)

    val displayWeekday = userSettings.settingStatusFlow.map {
        it.isWeekday
    }
    //var displayWeekday  by mutableStateOf(true)

    var selectedDate: LocalDate by mutableStateOf(LocalDate.now())


    fun setVisibleMonth(yearMonth: YearMonth) {
        visibleMonthState.value = yearMonth
    }

    fun resetToCurrentMonth() {
        visibleMonthState.value = YearMonth.now()
    }

    fun setIsGoBackToday(bool: Boolean) {
        isGoBackToday.value = bool
    }

    fun updateDarkTheme(isDark: String) {
        viewModelScope.launch {
            userSettings.updateIsDark(isDark)
        }
    }

    fun updateHideWeather(display: Boolean) {
        viewModelScope.launch {
            userSettings.updateIsHideWeather(display)
        }
    }

    fun updateHighlight(display: Boolean) {
        viewModelScope.launch {
            userSettings.updateIsHighlight(display)
        }
    }

    fun updateFirstDayOfWeek(dayOfWeek: String) {
        viewModelScope.launch {
            val firstDay = when (dayOfWeek) {
                "周六" -> DayOfWeek.SATURDAY
                "周日" -> DayOfWeek.SUNDAY
                "周一" -> DayOfWeek.MONDAY
                else -> DayOfWeek.MONDAY
            }
            userSettings.updateFirstDayOfWeek(firstDay)
        }
    }

    fun updateDisplayHoliday(isDisplay: Boolean) {
        viewModelScope.launch {
            userSettings.updateIsHoliday(isDisplay)
        }
    }

    fun updateDisplayFestival(isDisplay: Boolean) {
        viewModelScope.launch {
            userSettings.updateIsFestival(isDisplay)
        }
    }

    fun updateDisplayLunar(isDisplay: Boolean) {
        viewModelScope.launch {
            userSettings.updateIsLunar(isDisplay)
        }
    }

    fun updateDisplayWeek(isDisplay: Boolean) {
        viewModelScope.launch {
            userSettings.updateIsWeekDay(isDisplay)
        }
    }

}