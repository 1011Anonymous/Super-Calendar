package com.example.supercalendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.data.EventRepository
import com.example.supercalendar.datastore.StoreUserSettings
import com.example.supercalendar.domain.model.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository,
    userSettings: StoreUserSettings
) : ViewModel() {
    var eventForUpdate by mutableStateOf(
        Event(
            description = "",
            startDate = LocalDate.now(),
            advance = "",
            category = 0
        )
    )
        private set

    var eventForInsert by mutableStateOf(
        Event(
            description = "",
            startDate = LocalDate.now(),
            //notifyDate = LocalDate.now(),
            //notifyTime = LocalTime.now(),
            advance = "",
            category = 0
        )
    )

    //默认提醒时间
    val notification = userSettings.settingStatusFlow.map {
        it.notification
    }
    val schedule = userSettings.settingStatusFlow.map {
        it.schedule
    }

    var notificationWay1 by mutableStateOf("")
    var notificationWay2 by mutableStateOf("")
    var intervalText by mutableStateOf("不重复")

    //Alarm
    var isNotify by mutableStateOf(true)
    var isRepeat by mutableStateOf(false)
    var year by mutableIntStateOf(2024)
    var month by mutableIntStateOf(4)
    var day by mutableIntStateOf(23)
    var hour by mutableIntStateOf(18)
    var minute by mutableIntStateOf(30)
    var second by mutableIntStateOf(0)
    var interval by mutableLongStateOf(0)

    //userSettings

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

    fun getEventsByDate(date: LocalDate): Flow<List<Event>> = repository.getEventsByDate(date)

    fun updateDescription(newValue: String) {
        eventForUpdate = eventForUpdate.copy(description = newValue)
    }

    fun updateIsAllDay(newValue: Boolean) {
        eventForUpdate = eventForUpdate.copy(isAllDay = newValue)
    }

    fun updateStartTime(newValue: LocalTime) {
        eventForUpdate = eventForUpdate.copy(startTime = newValue)
    }

    fun updateEndTime(newValue: LocalTime) {
        eventForUpdate = eventForUpdate.copy(endTime = newValue)
    }

    fun updateStartDate(newValue: LocalDate) {
        eventForUpdate = eventForUpdate.copy(startDate = newValue)
    }

    fun updateEndDate(newValue: LocalDate) {
        eventForUpdate = eventForUpdate.copy(endDate = newValue)
    }

    fun updateDeparture(newValue: String) {
        eventForUpdate = eventForUpdate.copy(departurePlace = newValue)
    }

    fun updateArrive(newValue: String) {
        eventForUpdate = eventForUpdate.copy(arrivePlace = newValue)
    }

    fun updateAdvance(newValue: String) {
        eventForUpdate = eventForUpdate.copy(advance = newValue)
    }

    fun updateRepeat(newValue: String) {
        eventForUpdate = eventForUpdate.copy(repeat = newValue)
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

    fun updateIntervalText(newValue: String) {
        intervalText = newValue
    }

    fun updateIsNotify(newValue: Boolean) {
        isNotify = newValue
    }

    fun updateIsRepeat(newValue: Boolean) {
        isRepeat = newValue
    }

    fun updateInterval(newValue: Long) {
        interval = newValue
    }

    fun updateNotifyForInsert(newValue1: LocalDate, newValue2: LocalTime) {
        eventForInsert = eventForInsert.copy(
            notifyDate = newValue1,
            notifyTime = newValue2
        )
    }

    fun updateRepeatForInsert(newValue: String) {
        eventForInsert = eventForInsert.copy(
            repeat = newValue
        )
    }
}