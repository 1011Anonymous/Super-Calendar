package com.example.supercalendar.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.YearMonth

class HomeViewModel: ViewModel() {
    val visibleMonthState = mutableStateOf(YearMonth.now())

    fun setVisibleMonth(yearMonth: YearMonth) {
        visibleMonthState.value = yearMonth
    }

    fun resetToCurrentMonth() {
        visibleMonthState.value = YearMonth.now()
    }

}