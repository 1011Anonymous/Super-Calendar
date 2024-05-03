package com.example.supercalendar

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.supercalendar.notification.NotificationHandler
import java.util.Calendar
import kotlin.random.Random

class MyAlarm : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHandler = NotificationHandler(context)
        val repeatType = intent.getIntExtra("RepeatType", 0)
        val timeInMillis = intent.getLongExtra("TimeInMillis", System.currentTimeMillis())
        val uniqueId = intent.getIntExtra("UniqueID", Random.nextInt())
        val title = intent.getStringExtra("ContentTitle")
        val text = intent.getStringExtra("ContentText")

        notificationHandler.showNotification(
            uniqueId,
            title!!,
            text!!
        )
        Log.d("AlarmNotification", "Notification Set $uniqueId $title $text")

        if (repeatType != 0) {
            val alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance().apply {
                this.timeInMillis = System.currentTimeMillis()
                setTimeInMillis(timeInMillis)
            }
            when (repeatType) {
                1 -> {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    val alarmIntent = Intent(context, MyAlarm::class.java).let { intent1 ->
                        intent1.putExtra("RepeatType", repeatType)
                        intent1.putExtra("TimeInMillis", calendar.timeInMillis)
                        intent1.putExtra("UniqueID", uniqueId)
                        intent1.putExtra("ContentTitle", title)
                        intent1.putExtra("ContentText", text)

                        PendingIntent.getBroadcast(
                            context,
                            uniqueId,
                            intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    }
                    val alarmClockInfo = AlarmClockInfo(calendar.timeInMillis, alarmIntent)
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setAlarmClock(
                            alarmClockInfo,
                            alarmIntent
                        )
                        Log.d("AlarmClock", "Clock Set")
                    } else {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            )
                        )
                    }
                }
                2 -> {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1)
                    val alarmIntent = Intent(context, MyAlarm::class.java).let { intent1 ->
                        intent1.putExtra("RepeatType", repeatType)
                        intent1.putExtra("TimeInMillis", calendar.timeInMillis)
                        intent1.putExtra("UniqueID", uniqueId)
                        intent1.putExtra("ContentTitle", title)
                        intent1.putExtra("ContentText", text)

                        PendingIntent.getBroadcast(
                            context,
                            uniqueId,
                            intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    }
                    val alarmClockInfo = AlarmClockInfo(calendar.timeInMillis, alarmIntent)
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setAlarmClock(
                            alarmClockInfo,
                            alarmIntent
                        )
                        Log.d("AlarmClock", "Clock Set")
                    } else {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            )
                        )
                    }
                }
                3 -> {
                    calendar.add(Calendar.MONTH, 1)
                    val alarmIntent = Intent(context, MyAlarm::class.java).let { intent1 ->
                        intent1.putExtra("RepeatType", repeatType)
                        intent1.putExtra("TimeInMillis", calendar.timeInMillis)
                        intent1.putExtra("UniqueID", uniqueId)
                        intent1.putExtra("ContentTitle", title)
                        intent1.putExtra("ContentText", text)

                        PendingIntent.getBroadcast(
                            context,
                            uniqueId,
                            intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    }
                    val alarmClockInfo = AlarmClockInfo(calendar.timeInMillis, alarmIntent)
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setAlarmClock(
                            alarmClockInfo,
                            alarmIntent
                        )
                        Log.d("AlarmClock", "Clock Set")
                    } else {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            )
                        )
                    }
                }
                4 -> {
                    calendar.add(Calendar.YEAR, 1)
                    val alarmIntent = Intent(context, MyAlarm::class.java).let { intent1 ->
                        intent1.putExtra("RepeatType", repeatType)
                        intent1.putExtra("TimeInMillis", calendar.timeInMillis)
                        intent1.putExtra("UniqueID", uniqueId)
                        intent1.putExtra("ContentTitle", title)
                        intent1.putExtra("ContentText", text)

                        PendingIntent.getBroadcast(
                            context,
                            uniqueId,
                            intent1,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    }
                    val alarmClockInfo = AlarmClockInfo(calendar.timeInMillis, alarmIntent)
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setAlarmClock(
                            alarmClockInfo,
                            alarmIntent
                        )
                        Log.d("AlarmClock", "Clock Set")
                    } else {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            )
                        )
                    }
                }
            }
        }
    }
}