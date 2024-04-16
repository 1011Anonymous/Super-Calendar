package com.example.supercalendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.supercalendar.datastore.StoreUserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userSettings: StoreUserSettings
): ViewModel() {

    val visibleMonthState = mutableStateOf(YearMonth.now())
    val isGoBackToday = mutableStateOf(false)

    var hideWeather by mutableStateOf(false)
    var highlightWeekendsState by mutableStateOf(false)
    var firstDayOfWeek by mutableStateOf(DayOfWeek.MONDAY)

    private val _displayHoliday = userSettings.settingStatusFlow.map {
        it.isHoliday
    }
    var displayHoliday by mutableStateOf(true)

    var displayLunar by mutableStateOf(true)
    var displayFestival by mutableStateOf(true)
    var displayDayOfWeek  by mutableStateOf(true)




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

    fun updateDisplayHoliday() {
        displayHoliday = !displayHoliday
    }

    fun updateDisplayFestival() {
        displayFestival = !displayFestival
    }

    fun updateDisplayLunar() {
        displayLunar = !displayLunar
    }

    fun updateDisplayWeek() {
        displayDayOfWeek = !displayDayOfWeek
    }

}