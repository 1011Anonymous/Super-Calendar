package com.example.supercalendar.cache

import com.example.supercalendar.cache.model.WeatherCache

class CacheRepository(
    private val dao: CacheDao
) {
    suspend fun insertCache(cache: WeatherCache): Unit = dao.insert(cache)
}