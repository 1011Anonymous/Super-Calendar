package com.example.supercalendar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.supercalendar.domain.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM Event WHERE id = :id")
    suspend fun getEventById(id: Int): Event

    @Query("SELECT * FROM Event")
    suspend fun getAll(): Flow<List<Event>>

}