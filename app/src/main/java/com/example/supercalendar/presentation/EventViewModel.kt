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
    var eventForUpdate by mutableStateOf(
        Event(
            description = "",
            startDate = "",
            category = 0
        )
    )
        private set

    var eventForInsert by mutableStateOf(
        Event(
            description = "",
            startDate = "",
            category = 0
        )
    )

    var notificationWay1 by mutableStateOf("")
    var notificationWay2 by mutableStateOf("")
    var interval by mutableStateOf("")

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
            eventForUpdate = repository.getEventById(id)
        }
    }

    fun getEventsByDate(date: String): Flow<List<Event>> = repository.getEventsByDate(date)

    fun updateDescription(newValue: String) {
        eventForUpdate = eventForUpdate.copy(description = newValue)
    }

    fun updateIsAllDay(newValue: Boolean) {
        eventForUpdate = eventForUpdate.copy(isAllDay = newValue)
    }

    fun updateStartTime(newValue: String) {
        eventForUpdate = eventForUpdate.copy(startTime = newValue)
    }

    fun updateEndTime(newValue: String) {
        eventForUpdate = eventForUpdate.copy(endTime = newValue)
    }

    fun updateStartDate(newValue: String) {
        eventForUpdate = eventForUpdate.copy(startDate = newValue)
    }

    fun updateEndDate(newValue: String) {
        eventForUpdate = eventForUpdate.copy(endDate = newValue)
    }

    fun updateDeparture(newValue: String) {
        eventForUpdate = eventForUpdate.copy(departurePlace = newValue)
    }

    fun updateArrive(newValue: String) {
        eventForUpdate = eventForUpdate.copy(arrivePlace = newValue)
    }

    fun updateCategory(newValue: Int) {
        eventForUpdate = eventForUpdate.copy(category = newValue)
    }

    fun updateNotificationWay1(newValue: String) {
        notificationWay1 = newValue
    }

    fun updateNotificationWay2(newValue: String) {
        notificationWay2 = newValue
    }

    fun updateInterval(newValue: String) {
        interval = newValue
    }

}