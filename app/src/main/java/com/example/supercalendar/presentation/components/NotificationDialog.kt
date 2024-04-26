package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.utils.TimeUtils
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun NotificationDialog1(
    openDialog: Boolean,
    eventViewModel: EventViewModel,
    onClose: () -> Unit
) {
    val options = listOf("不提醒", "任务发生时", "5分钟前", "15分钟前", "30分钟前", "1小时前")
    var selectedOption: String by remember {
        mutableStateOf(options[1])
    }

    if (openDialog) {
        AlertDialog(
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedOption == option,
                                    onClick = {
                                        selectedOption = option
                                        eventViewModel.updateNotificationWay1(option)
                                        //println(eventViewModel.notificationWay1)
                                        /*
                                        when (option) {
                                            "不提醒" -> {
                                                eventViewModel.updateIsNotify(false)
                                            }
                                            "5分钟前" -> {
                                                val scheduledTime = LocalDateTime.of(
//                                                    eventViewModel.year,
//                                                    eventViewModel.month,
//                                                    eventViewModel.day,
//                                                    eventViewModel.hour,
//                                                    eventViewModel.minute
                                                    eventViewModel.eventForInsert.startDate,
                                                    eventViewModel.eventForInsert.startTime
                                                )
                                                val newTime = TimeUtils.subtractTimeFromDateTime(
                                                    scheduledTime,
                                                    5,
                                                    0
                                                )
//                                                eventViewModel.updateYear(newTime.year)
//                                                eventViewModel.updateMonth(newTime.monthValue)
//                                                eventViewModel.updateDay(newTime.dayOfMonth)
//                                                eventViewModel.updateHour(newTime.hour)
//                                                eventViewModel.updateMinute(newTime.minute)
                                                eventViewModel.updateNotifyForInsert(
                                                    newTime.toLocalDate(),
                                                    newTime.toLocalTime()
                                                )
                                            }
                                            "15分钟前" -> {
                                                val scheduledTime = LocalDateTime.of(
                                                    eventViewModel.eventForInsert.startDate,
                                                    eventViewModel.eventForInsert.startTime
                                                )
                                                val newTime = TimeUtils.subtractTimeFromDateTime(
                                                    scheduledTime,
                                                    15,
                                                    0
                                                )
                                                eventViewModel.updateNotifyForInsert(
                                                    newTime.toLocalDate(),
                                                    newTime.toLocalTime()
                                                )
                                            }
                                            "30分钟前" -> {
                                                val scheduledTime = LocalDateTime.of(
                                                    eventViewModel.eventForInsert.startDate,
                                                    eventViewModel.eventForInsert.startTime
                                                )
                                                val newTime = TimeUtils.subtractTimeFromDateTime(
                                                    scheduledTime,
                                                    30,
                                                    0
                                                )
                                                eventViewModel.updateNotifyForInsert(
                                                    newTime.toLocalDate(),
                                                    newTime.toLocalTime()
                                                )
                                            }
                                            "1小时前" -> {
                                                val scheduledTime = LocalDateTime.of(
                                                    eventViewModel.eventForInsert.startDate,
                                                    eventViewModel.eventForInsert.startTime
                                                )
                                                val newTime = TimeUtils.subtractTimeFromDateTime(
                                                    scheduledTime,
                                                    0,
                                                    1
                                                )
                                                eventViewModel.updateNotifyForInsert(
                                                    newTime.toLocalDate(),
                                                    newTime.toLocalTime()
                                                )
                                            }
                                            "任务发生时" -> {
                                                eventViewModel.updateNotifyForInsert(
                                                    eventViewModel.eventForInsert.startDate,
                                                    eventViewModel.eventForInsert.startTime!!
                                                )
                                            }
                                        }
                                        */
                                        onClose()
                                    }
                                )
                                .padding(16.dp),
                        ) {
                            RadioButton(
                                selected = selectedOption == option,
                                onClick = null
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            },
            onDismissRequest = { onClose() },
            confirmButton = { /*TODO*/ }
        )
    }
}

@Composable
fun NotificationDialog2(
    openDialog: Boolean,
    eventViewModel: EventViewModel,
    onClose: () -> Unit
) {
    val options = listOf("不提醒", "任务发生当天", "1天前", "2天前", "3天前", "1周前")
    var selectedOption: String by remember {
        mutableStateOf(options[1])
    }

    if (openDialog) {
        AlertDialog(

            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedOption == option,
                                    onClick = {
                                        selectedOption = option
                                        eventViewModel.updateNotificationWay2(option)
                                        /*
                                        when (option) {
                                            "不提醒" -> {
                                                eventViewModel.updateIsNotify(false)
                                            }
                                            "1天前" -> {
                                                eventViewModel.updateNotifyForInsert(
                                                    newValue1 = eventViewModel.eventForInsert.startDate.minusDays(1),
                                                    newValue2 = LocalTime.of(9,0)
                                                )
                                            }
                                            "2天前" -> {
                                                eventViewModel.updateNotifyForInsert(
                                                    newValue1 = eventViewModel.eventForInsert.startDate.minusDays(2),
                                                    newValue2 = LocalTime.of(9,0)
                                                )
                                            }
                                            "3天前" -> {
                                                eventViewModel.updateNotifyForInsert(
                                                    newValue1 = eventViewModel.eventForInsert.startDate.minusDays(3),
                                                    newValue2 = LocalTime.of(9,0)
                                                )
                                            }
                                            "1周前" -> {
                                                eventViewModel.updateNotifyForInsert(
                                                    newValue1 = eventViewModel.eventForInsert.startDate.minusDays(7),
                                                    newValue2 = LocalTime.of(9,0)
                                                )
                                            }
                                            else -> {
                                                eventViewModel.updateNotifyForInsert(
                                                    newValue1 = eventViewModel.eventForInsert.startDate,
                                                    newValue2 = LocalTime.of(9,0)
                                                )
                                            }
                                        }
                                        */
                                        onClose()
                                    }
                                )
                                .padding(16.dp),
                        ) {
                            RadioButton(
                                selected = selectedOption == option,
                                onClick = null
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            },
            onDismissRequest = { onClose() },
            confirmButton = { /*TODO*/ }
        )
    }
}

