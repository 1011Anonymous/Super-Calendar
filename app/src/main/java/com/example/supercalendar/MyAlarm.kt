package com.example.supercalendar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.supercalendar.notification.NotificationHandler
import kotlin.random.Random

class MyAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHandler = NotificationHandler(context)
        val uniqueId = intent.getIntExtra("UniqueID", Random.nextInt())
        val title = intent.getStringExtra("ContentTitle")
        val text = intent.getStringExtra("ContentText")
//        val title = "Test"
//        val text = "Hello World"
        notificationHandler.showNotification(
            uniqueId,
            title!!,
            text!!
        )
        Log.d("AlarmNotification", "Notification Set $uniqueId $title $text")
    }
}