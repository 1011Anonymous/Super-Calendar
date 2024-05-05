package com.example.supercalendar.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.supercalendar.MainActivity
import com.example.supercalendar.R
import kotlin.random.Random

class NotificationHandler(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = context.getString(R.string.notification_channel_id)


    fun showNotification(
        id: Int,
        title: String,
        text: String
    ) {
        val notificationIntent = Intent(context, MainActivity::class.java).let { intent ->
            PendingIntent.getActivity(
                context,
                id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setSmallIcon(R.drawable.outline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(notificationIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id, notification)
    }
}