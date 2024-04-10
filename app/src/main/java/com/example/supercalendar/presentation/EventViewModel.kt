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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
): ViewModel() {
    var event by mutableStateOf(
        Event(
            description = "",
            isImportant = false,
            time = "",
            duration = "",
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

    fun updateDescription(newValue: String) {
        event = event.copy(description = newValue)
    }

    fun updateIsImportant(newValue: Boolean) {
        event = event.copy(isImportant = newValue)
    }

    fun updateTime(newValue: String) {
        event = event.copy(time = newValue)
    }

    fun updateDuration(newValue: String) {
        event = event.copy(duration = newValue)
    }
}