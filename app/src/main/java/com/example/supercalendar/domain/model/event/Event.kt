package com.example.supercalendar.domain.model.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val description: String,
    val isAllDay: Boolean,
    val isImportant: Boolean,
    val startDate: String,
    val endDate: String? = null,
    val startTime: String,
    val endTime: String? = null,
    //0->提醒，1->日程，2->生日，3->出行
    val category: Int

)
