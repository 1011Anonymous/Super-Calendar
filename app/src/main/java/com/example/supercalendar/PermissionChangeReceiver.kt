package com.example.supercalendar

import android.app.AlarmManager
import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class PermissionChangeReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED) {
            if (context.getSystemService(AlarmManager::class.java).canScheduleExactAlarms()) {
                Log.d("Permission", "Exact Alarm Clock Permission is Granted")
            }
        }
    }
}