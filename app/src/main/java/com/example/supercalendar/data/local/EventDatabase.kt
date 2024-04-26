package com.example.supercalendar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.supercalendar.data.converter.DateTimeConverters
import com.example.supercalendar.domain.model.event.Event

@Database(entities = [Event::class], version = 1)
@TypeConverters(DateTimeConverters::class)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao
}