package com.example.supercalendar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.supercalendar.domain.model.event.Event

@Database(entities = [Event::class], version = 1)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao
}