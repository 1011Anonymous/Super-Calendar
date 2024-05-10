package com.example.supercalendar.presentation.update_screen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.supercalendar.MyAlarm
import com.example.supercalendar.R
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.components.AdvanceDialog2
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayUpdate(
    id: Int,
    eventViewModel: EventViewModel,
    onBack: () -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val desc = eventViewModel.eventForUpdate.description
    val date = eventViewModel.eventForUpdate.startDate
    val advance = eventViewModel.eventForUpdate.advance

    var event = eventViewModel.eventForUpdate
    val context = LocalContext.current
    val newID = event.uniqueId!!
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
    val alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager

    LaunchedEffect(key1 = true) {
        eventViewModel.getEventById(id)
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            this.timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        val newValue = DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)
                        eventViewModel.updateStartDate(newValue)
                        showDatePicker = false
                    }
                ) { Text("确定") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("取消") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "编辑生日") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        when (event.advance) {
                            "不提醒" -> {}
                            "1天前" -> {
                                eventViewModel.eventForUpdate.startTime?.let {
                                    eventViewModel.updateNotifyForUpdate(
                                        newValue1 = eventViewModel.eventForInsert.startDate.minusDays(1),
                                        newValue2 = it
                                    )
                                }
                            }
                            "2天前" -> {
                                eventViewModel.eventForUpdate.startTime?.let {
                                    eventViewModel.updateNotifyForUpdate(
                                        newValue1 = eventViewModel.eventForUpdate.startDate.minusDays(2),
                                        newValue2 = it
                                    )
                                }
                            }
                            "3天前" -> {
                                eventViewModel.eventForUpdate.startTime?.let {
                                    eventViewModel.updateNotifyForUpdate(
                                        newValue1 = eventViewModel.eventForUpdate.startDate.minusDays(3),
                                        newValue2 = it
                                    )
                                }
                            }
                            "1周前" -> {
                                eventViewModel.eventForUpdate.startTime?.let {
                                    eventViewModel.updateNotifyForUpdate(
                                        newValue1 = eventViewModel.eventForUpdate.startDate.minusDays(7),
                                        newValue2 = it
                                    )
                                }
                            }
                            else -> {
                                eventViewModel.updateNotifyForUpdate(
                                    eventViewModel.eventForUpdate.startDate,
                                    eventViewModel.eventForUpdate.startTime!!
                                )
                            }
                        }
                        event = eventViewModel.eventForUpdate
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
                        val alarmIntent: PendingIntent = Intent(context, MyAlarm::class.java).let { intent ->
                            Log.d("AlarmSetup", "Sending Alarm with ID: $newID, Title: $contentTitle")

                            intent.putExtra("RepeatType", 4)
                            intent.putExtra("UniqueID", newID)
                            intent.putExtra("ContentTitle", contentTitle)
                            intent.putExtra("ContentText", contentText)

                            PendingIntent.getBroadcast(
                                context,
                                newID,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )
                        }
                        val alarmClockInfo = AlarmManager.AlarmClockInfo(
                            calendar.timeInMillis,
                            alarmIntent
                        )
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
                        eventViewModel.updateEvent(eventViewModel.eventForUpdate)
                        onBack()
                    }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),
        ) {
            OutlinedTextField(
                value = desc,
                onValueChange = { newValue ->
                    eventViewModel.updateDescription(newValue)
                },
                placeholder = { Text(text = "请输入姓名") },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp)),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = { eventViewModel.updateDescription("") }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = null
                        )
                    }
                },
                textStyle = taskTextStyle
            )

            Spacer(modifier = Modifier.size(20.dp))

            Icon(painter = painterResource(id = R.drawable.outline_access_time_24), contentDescription = null)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDatePicker = true }
                ) {
                    Text(text = DateUtils.dateToString(date))
                    Spacer(modifier = Modifier.width(250.dp))
                }
            }
            Divider(modifier = Modifier.padding(bottom = 20.dp))

            Icon(
                painter = painterResource(id = R.drawable.outline_notifications_active_24),
                contentDescription = null
            )
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openDialog= true }
            ) {
                Text(text = advance)
                Spacer(modifier = Modifier.width(260.dp))
            }
        }
    }
    AdvanceDialog2(openDialog = openDialog, id = id, eventViewModel = eventViewModel) {
        openDialog = false
    }
}