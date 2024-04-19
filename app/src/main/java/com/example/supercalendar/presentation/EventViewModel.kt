package com.example.supercalendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.data.EventRepository
import com.example.supercalendar.domain.model.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
): ViewModel() {
    var event by mutableStateOf(
        Event(
            description = "",
            isAllDay = false,
            isImportant = false,
            startDate = "",
            startTime = "",
            category = 0
        )
    )
        private set

    val getAllEvents = repository.getAllEvents()
    private var deletedEvent: Event? = null

    fun insertEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEvent(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEvent(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEvent(event)
            deletedEvent = event
        }
    }

    fun undoDeleteEvent() {
        deletedEvent?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertEvent(it)
            }
        }
    }

    fun getEventById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            event = repository.getEventById(id)
        }
    }

    fun getEventsByDate(date: String): Flow<List<Event>> = repository.getEventsByDate(date)

    fun updateDescription(newValue: String) {
        event = event.copy(description = newValue)
    }

    fun updateIsImportant(newValue: Boolean) {
        event = event.copy(isImportant = newValue)
    }

    fun updateIsAllDay(newValue: Boolean) {
        event = event.copy(isAllDay = newValue)
    }

    fun updateStartTime(newValue: String) {
        event = event.copy(startTime = newValue)
    }

    fun updateEndTime(newValue: String) {
        event = event.copy(endTime = newValue)
    }

    fun updateStartDate(newValue: String) {
        event = event.copy(startDate = newValue)
    }

    fun updateEndDate(newValue: String) {
        event = event.copy(endDate = newValue)
    }

}