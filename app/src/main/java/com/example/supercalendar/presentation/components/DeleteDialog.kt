package com.example.supercalendar.presentation.components

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.supercalendar.MyAlarm
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils
import java.util.Calendar

@Composable
fun DeleteDialog(
    openDialog: Boolean,
    id: Int,
    eventViewModel: EventViewModel,
    onClose: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val event = eventViewModel.eventForUpdate

    LaunchedEffect(key1 = true) {
        eventViewModel.getEventById(id)
    }

    if (openDialog) {
        AlertDialog(
            text = {
                Text(text = "确定删除此事件？")
            },
            onDismissRequest = { onClose() },
            confirmButton = {
                TextButton(onClick = {
                    event.uniqueId?.let {
                        val repeatType = when (event.repeat) {
                            "每天" -> 1
                            "每周" -> 2
                            "每月" -> 3
                            "每年" -> 4
                            else -> 0
                        }
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = System.currentTimeMillis()
                            event.notifyDate?.let { date ->
                                event.notifyTime?.let {time ->
                                    set(
                                        date.year,
                                        date.monthValue - 1,
                                        date.dayOfMonth,
                                        time.hour,
                                        time.minute,
                                        0
                                    )
                                }
                            }
                        }
                        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
                        val contentTitle = when (event.category) {
                            0 -> "[提醒] ${event.description}"
                            1 -> "[日程] ${event.description}"
                            2 -> "[生日] ${event.description}"
                            else -> "[出行] ${event.description}"
                        }
                        val contentText = when (event.category) {
                            0 -> "${
                                event.startTime?.let {
                                    TimeUtils.convertLocalTimeToString(
                                        it
                                    )
                                }
                            }"

                            1 -> if (event.isAllDay == true) "${DateUtils.dateToString(event.startDate)} ~ ${
                                event.endDate?.let {
                                    DateUtils.dateToString(
                                        it
                                    )
                                }
                            }"
                            else "${DateUtils.dateToString(event.startDate)} ${
                                event.startTime?.let {
                                    TimeUtils.convertLocalTimeToString(
                                        it
                                    )
                                }
                            } ~ ${event.endDate?.let { DateUtils.dateToString(it) }} ${
                                event.endTime?.let {
                                    TimeUtils.convertLocalTimeToString(
                                        it
                                    )
                                }
                            }"

                            2 -> DateUtils.dateToString(event.startDate)
                            else -> "${
                                event.startTime?.let {
                                    TimeUtils.convertLocalTimeToString(
                                        it
                                    )
                                }
                            }"
                        }
                        val alarmIntent: PendingIntent =
                            Intent(context, MyAlarm::class.java).let { intent ->
                                intent.putExtra("RepeatType", repeatType)
                                intent.putExtra("TimeInMillis", calendar.timeInMillis)
                                intent.putExtra("UniqueID", it)
                                intent.putExtra("ContentTitle", contentTitle)
                                intent.putExtra("ContentText", contentText)

                                PendingIntent.getBroadcast(
                                    context,
                                    it,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                                )
                            }
                        alarmManager.cancel(alarmIntent)
                    }
                    eventViewModel.deleteEvent(event)
                    onBack()
                    onClose()
                }) {
                    Text(text = "确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { onClose() }) {
                    Text(text = "取消")
                }
            }
        )
    }
}

