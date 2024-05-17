package com.example.supercalendar.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.supercalendar.cache.model.WeatherCache

@Database(entities = [WeatherCache::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}