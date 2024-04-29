package com.example.supercalendar

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.supercalendar.notification.NotificationHandler
import kotlin.random.Random

class MyAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHandler = NotificationHandler(context)
        val uniqueId = intent.getIntExtra("UniqueID", 888888)
//        val title = intent.getStringExtra("ContentTitle")
//        val text = intent.getStringExtra("ContentText")
        val title = "Test"
        val text = "Hello World"

        Log.d("Data Intent", "$uniqueId $title $text")

        when (intent.action) {
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                notificationHandler.showNotification(
                    uniqueId,
                    title,
                    text
                )
                Log.d("Permission", "Schedule Exact Permission Granted")
            }
            else -> {
                notificationHandler.showNotification(
                    uniqueId,
                    title,
                    text
                )
                Log.d("Notification", "Notification Set $uniqueId $title $text")
            }
        }
    }
}