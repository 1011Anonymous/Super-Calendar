package com.example.supercalendar.presentation.event_screen

import android.Manifest
import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.supercalendar.MyAlarm
import com.example.supercalendar.R
import com.example.supercalendar.domain.model.event.Event
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.common.toastMsg
import com.example.supercalendar.ui.theme.topAppBarTextStyle
import com.example.supercalendar.utils.DateUtils.Companion.dateToString
import com.example.supercalendar.utils.TimeUtils.Companion.convertLocalTimeToString
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDate
import java.util.Calendar
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun EventScreen(
    eventViewModel: EventViewModel,
    onBack: () -> Unit
) {
    var event = eventViewModel.eventForInsert
    val context = LocalContext.current
    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    /*val contentTitle = when (event.category) {
        0 -> "[提醒] ${event.description}"
        1 -> "[日程] ${event.description}"
        2 -> "[生日] ${event.description}"
        else -> "[出行] ${event.description}"
    }
    val contentText = when (event.category) {
        0 -> "${
            event.startTime?.let {
                convertLocalTimeToString(
                    it
                )
            }
        }"

        1 -> if (event.isAllDay == true) "${dateToString(event.startDate)} ~ ${
            event.endDate?.let {
                dateToString(
                    it
                )
            }
        }"
        else "${dateToString(event.startDate)} ${
            event.startTime?.let {
                convertLocalTimeToString(
                    it
                )
            }
        } ~ ${event.endDate?.let { dateToString(it) }} ${
            event.endTime?.let {
                convertLocalTimeToString(
                    it
                )
            }
        }"

        2 -> dateToString(event.startDate)
        else -> "${
            event.startTime?.let {
                convertLocalTimeToString(
                    it
                )
            }
        }"
    }
    val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    val alarmIntent: PendingIntent = Intent(context, MyAlarm::class.java).let { intent ->
        Log.d("AlarmSetup", "Sending Alarm with ID: ${event.id}, Title: $contentTitle")

        intent.putExtra("UniqueID", event.id)
        intent.putExtra("ContentTitle", contentTitle)
        intent.putExtra("ContentText", contentText)

        PendingIntent.getBroadcast(
            context,
            event.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }*/

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "新建事件",
                        style = topAppBarTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Back to Home")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (event.description.isNotBlank()) {
                            val newID = Random.nextInt()
                            val contentTitle = when (event.category) {
                                0 -> "[提醒] ${event.description}"
                                1 -> "[日程] ${event.description}"
                                2 -> "[生日] ${event.description}"
                                else -> "[出行] ${event.description}"
                            }
                            val contentText = when (event.category) {
                                0 -> "${
                                    event.startTime?.let {
                                        convertLocalTimeToString(
                                            it
                                        )
                                    }
                                }"

                                1 -> if (event.isAllDay == true) "${dateToString(event.startDate)} ~ ${
                                    event.endDate?.let {
                                        dateToString(
                                            it
                                        )
                                    }
                                }"
                                else "${dateToString(event.startDate)} ${
                                    event.startTime?.let {
                                        convertLocalTimeToString(
                                            it
                                        )
                                    }
                                } ~ ${event.endDate?.let { dateToString(it) }} ${
                                    event.endTime?.let {
                                        convertLocalTimeToString(
                                            it
                                        )
                                    }
                                }"

                                2 -> dateToString(event.startDate)
                                else -> "${
                                    event.startTime?.let {
                                        convertLocalTimeToString(
                                            it
                                        )
                                    }
                                }"
                            }
                            val alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager

                            when (event.advance) {
                                "不提醒" -> {}
                                "任务发生时" -> {
                                    eventViewModel.updateNotifyForInsert(
                                        eventViewModel.eventForInsert.startDate,
                                        eventViewModel.eventForInsert.startTime!!
                                    )
                                    event = eventViewModel.eventForInsert
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

                                    if (event.repeat == null || event.repeat == "不重复") {
                                        val alarmIntent = Intent(context, MyAlarm::class.java).let { intent ->
                                            Log.d("AlarmSetup", "Sending Alarm with ID: $newID, Title: $contentTitle")

                                            intent.putExtra("RepeatType", 0)
                                            intent.putExtra("TimeInMillis", calendar.timeInMillis)
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
                                                    ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                                                )
                                            )
                                        }
                                    } else {
                                        when (event.repeat) {
                                            "每天" -> {
                                                val alarmIntent: PendingIntent = Intent(context, MyAlarm::class.java).let { intent ->
                                                    Log.d("AlarmSetup", "Sending Alarm with ID: $newID, Title: $contentTitle")

                                                    intent.putExtra("RepeatType", 1)
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
                                                            ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                                                        )
                                                    )
                                                }
                                            }
                                            "每周" -> {
                                                val alarmIntent: PendingIntent = Intent(context, MyAlarm::class.java).let { intent ->
                                                    Log.d("AlarmSetup", "Sending Alarm with ID: $newID, Title: $contentTitle")

                                                    intent.putExtra("RepeatType", 2)
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
                                                            ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                                                        )
                                                    )
                                                }
                                            }
                                            "每月" -> {
                                                val alarmIntent: PendingIntent = Intent(context, MyAlarm::class.java).let { intent ->
                                                    Log.d("AlarmSetup", "Sending Alarm with ID: $newID, Title: $contentTitle")

                                                    intent.putExtra("RepeatType", 3)
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
                                                            ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                                                        )
                                                    )
                                                }
                                            }
                                            "每年" -> {
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
                                                            ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    event = event.copy(uniqueId = newID)
                                }
                            }

                            eventViewModel.insertEvent(event)
                            event = Event(
                                description = "",
                                startDate = LocalDate.now(),
                                advance = "",
                                category = 0
                            )
                            onBack()
                        } else {
                            toastMsg(
                                context = context,
                                msg = "请输入相关内容"
                            )
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            val pagerState = rememberPagerState(initialPage = 0) {
                4
            }

            var selectedTab by remember {
                mutableIntStateOf(pagerState.currentPage)
            }

            LaunchedEffect(key1 = selectedTab) {
                pagerState.scrollToPage(selectedTab)
            }

            LaunchedEffect(key1 = pagerState.currentPage) {
                selectedTab = pagerState.currentPage
            }

            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.padding(8.dp)
            ) {
                for (index in 0 until pagerState.pageCount) {
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    ) {
                        val id = when (index) {
                            0 -> R.drawable.outline_notifications_24
                            1 -> R.drawable.outline_event_note_24
                            2 -> R.drawable.outline_cake_24
                            else -> R.drawable.outline_card_travel_24
                        }

                        val text = when (index) {
                            0 -> "提醒"
                            1 -> "日程"
                            2 -> "生日"
                            else -> "出行"
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(30.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = null
                            )
                        }

                        Text(
                            text = text,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            HorizontalPager(state = pagerState) {


                when (it) {
                    0 -> ReminderScreen(eventViewModel = eventViewModel)
                    1 -> TaskScreen(eventViewModel = eventViewModel)
                    2 -> BirthdayScreen(eventViewModel = eventViewModel)
                    else -> TravelScreen(eventViewModel = eventViewModel)
                }

            }
        }
    }
}

@Composable
fun ScreenTest(page: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${page + 1}")
    }
}




