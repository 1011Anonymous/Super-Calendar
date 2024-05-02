package com.example.supercalendar.domain.model.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Event(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val description: String,
    val isAllDay: Boolean? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val notifyDate: LocalDate? = null,
    val notifyTime: LocalTime? = null,
    val advance: String,
    val repeat: String? = null,
    val departurePlace: String? = null,
    val arrivePlace: String? = null,
    val uniqueId: Int? = null,
    //0->提醒，1->日程，2->生日，3->出行
    val category: Int

)
