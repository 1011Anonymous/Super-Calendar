package com.example.supercalendar.domain.model.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val description: String,
    val isImportant: Boolean,
    val time: String,
    val duration: String,
    //1->提醒，2->日程，3->生日，4->出行
    val category: Int = 1,

)
