package com.example.supercalendar.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.supercalendar.domain.model.event.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM Event WHERE id = :id")
    suspend fun getEventById(id: Int): Event

    @Query("""
        SELECT * FROM Event 
        ORDER BY startDate ASC, startTime ASC
    """)
    fun getAll(): Flow<List<Event>>

    @Query("""
        SELECT * FROM Event 
        WHERE :date BETWEEN startDate AND COALESCE(endDate, startDate)
        ORDER BY startTime ASC
    """)
    fun getEventsByDate(date: LocalDate): Flow<List<Event>>

}