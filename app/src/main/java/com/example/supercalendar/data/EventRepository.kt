package com.example.supercalendar.data

import com.example.supercalendar.data.local.EventDao
import com.example.supercalendar.domain.model.event.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(
    private val dao: EventDao
) {
    suspend fun insertEvent(event: Event): Unit = dao.insert(event)

    suspend fun updateEvent(event: Event): Unit = dao.update(event)

    suspend fun deleteEvent(event: Event): Unit = dao.delete(event)

    suspend fun getEventById(id: Int): Event = dao.getEventById(id)

    fun getAllEvents(): Flow<List<Event>> = dao.getAll()

    fun getEventsByDate(date: String): Flow<List<Event>> = dao.getEventsByDate(date)
}