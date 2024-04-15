package com.example.supercalendar.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.YearMonth

class HomeViewModel: ViewModel() {
    val visibleMonthState = mutableStateOf(YearMonth.now())
    val isGoBackToday = mutableStateOf(false)



    fun setVisibleMonth(yearMonth: YearMonth) {
        visibleMonthState.value = yearMonth
    }

    fun resetToCurrentMonth() {
        visibleMonthState.value = YearMonth.now()
    }

    fun setIsGoBackToday(bool: Boolean) {
        isGoBackToday.value = bool
    }

}