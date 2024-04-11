package com.example.supercalendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.domain.model.holiday.Holiday
import com.example.supercalendar.network.holiday.HolidayRepository
import com.example.supercalendar.network.holiday.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

/*
@HiltViewModel
class HolidayViewModel @Inject constructor(
    private val repository: HolidayRepository
) : ViewModel() {
    private val _holidays = MutableStateFlow<List<Holiday>>(emptyList())
    val holidays = _holidays.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getResult(date: String) {
        viewModelScope.launch {
            repository.getHolidayList(date).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { holidays ->
                            _holidays.update { holidays }
                        }
                    }
                }
            }
        }
    }
}
*/

@HiltViewModel
class HolidayViewModel @Inject constructor(
    private val repository: HolidayRepository
) : ViewModel() {
    private val _holidays = MutableStateFlow<List<Holiday>>(emptyList())
    val holidays = _holidays.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()



    fun loadHolidaysForSurroundingMonths(yearMonth: YearMonth) {
        viewModelScope.launch {
            val lastMonthDate = yearMonth.minusMonths(1).toString()
            val currentMonthDate = yearMonth.toString()
            val nextMonthDate = yearMonth.plusMonths(1).toString()

            try {
                // Launch concurrent API requests
                val lastMonthHolidaysDeferred = async { fetchHolidays(lastMonthDate) }
                val currentMonthHolidaysDeferred = async { fetchHolidays(currentMonthDate) }
                val nextMonthHolidaysDeferred = async { fetchHolidays(nextMonthDate) }

                // Await results
                val lastMonthHolidays = lastMonthHolidaysDeferred.await()
                val currentMonthHolidays = currentMonthHolidaysDeferred.await()
                val nextMonthHolidays = nextMonthHolidaysDeferred.await()

                // Combine the results into a single list
                val combinedHolidays = lastMonthHolidays + currentMonthHolidays + nextMonthHolidays
                _holidays.update { combinedHolidays }
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }

    // Helper function to perform the API request and handle the response
    private suspend fun fetchHolidays(date: String): List<Holiday> {
        val holidays = mutableListOf<Holiday>()
        repository.getHolidayList(date).collect { result ->
            when (result) {
                is Result.Success -> {
                    holidays.addAll(result.data ?: emptyList())
                }
                is Result.Error -> {
                    _showErrorToastChannel.trySend(true).isSuccess
                }
            }
        }
        return holidays
    }


}


