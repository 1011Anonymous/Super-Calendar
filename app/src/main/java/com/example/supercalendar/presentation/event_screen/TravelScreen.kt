package com.example.supercalendar.presentation.event_screen

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
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supercalendar.R
import com.example.supercalendar.constant.Const
import com.example.supercalendar.domain.model.event.Event
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.components.NotificationDialog1
import com.example.supercalendar.presentation.components.TimePickerDialog
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelScreen(eventViewModel: EventViewModel) {
    var openNotification by remember {
        mutableStateOf(false)
    }

    val defaultNotification by eventViewModel.notification.collectAsState(initial = "")
    LaunchedEffect(key1 = true) {  // key1 can be a specific condition or variable
        eventViewModel.updateNotificationWay1(defaultNotification)
    }


    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
    ) {
        var text by remember {
            mutableStateOf("")
        }
        var departure by remember {
            mutableStateOf("")
        }
        var arrive by remember {
            mutableStateOf("")
        }
        val focusRequester = remember {
            FocusRequester()
        }
        val focusManager = LocalFocusManager.current

        val datePickerState = rememberDatePickerState()
        var showDatePicker by remember {
            mutableStateOf(false)
        }

        val timePickerState = rememberTimePickerState(is24Hour = true)
        var showTimePicker by remember {
            mutableStateOf(false)
        }

        var date by remember {
            mutableStateOf(LocalDate.now())
        }
        var time by remember {
            mutableStateOf(LocalTime.now())
        }

        eventViewModel.eventForInsert = Event(
            description = text,
            startDate = date,
            startTime = time,
            advance = eventViewModel.notificationWay1,
            repeat = "不重复",
            departurePlace = departure,
            arrivePlace = arrive,
            category = 3
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
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
                            date = DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)

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

        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val cal = Calendar.getInstance()
                            cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            cal.set(Calendar.MINUTE, timePickerState.minute)
                            cal.isLenient = false
                            time = TimeUtils.convertMillisToLocalTime(cal.timeInMillis)
                            showTimePicker = false
                        }
                    ) { Text("确定") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showTimePicker = false
                        }
                    ) { Text("取消") }
                }
            ) {
                TimePicker(state = timePickerState)
            }
        }

        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            placeholder = { Text(text = "请输入航班号或车次号，如CZ8888") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp))
                .focusRequester(focusRequester),
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
                IconButton(onClick = { text = "" }) {
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
            TextButton(onClick = { showDatePicker = true }) {
                Text(text = DateUtils.dateToString(date))
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = { showTimePicker = true }) {
                Text(text = TimeUtils.convertLocalTimeToString(time))
            }
        }
        Divider(modifier = Modifier.padding(bottom = 20.dp))

        Icon(
            painter = painterResource(id = R.drawable.outline_notifications_active_24),
            contentDescription = null
        )

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { openNotification = true }
        ) {
            Text(text = eventViewModel.notificationWay1)
            // 将EventViewModel添加到SettingScreen中方便设置默认提醒时间
            // SettingScreen -> notificationWay1, notificationWay1-> text, NotificationDialog -> notificationWay1
            // 在EventScreen中判断notificationWay1来进行获取提醒时间，删除NotificationDialog中判断提醒时间的部分
            // 判断后，在EventScreen中最将notificationWay1恢复到设置中的值
            Spacer(modifier = Modifier.width(260.dp))
        }
        Divider(modifier = Modifier.padding(bottom = 20.dp))

        Icon(painter = painterResource(id = R.drawable.outline_location_on_24), contentDescription = null)

        OutlinedTextField(
            value = departure,
            onValueChange = {departure = it},
            placeholder = { Text(text = "请输入出发地点")},
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(vertical = 10.dp)
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
                IconButton(onClick = { departure = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = null
                    )
                }
            },
            textStyle = taskTextStyle
        )
        Divider(modifier = Modifier.padding(bottom = 20.dp))

        Icon(painter = painterResource(id = R.drawable.outline_location_on_24), contentDescription = null)

        OutlinedTextField(
            value = arrive,
            onValueChange = {arrive = it},
            placeholder = { Text(text = "请输入到达地点")},
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(vertical = 10.dp)
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
                IconButton(onClick = { arrive = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = null
                    )
                }
            },
            textStyle = taskTextStyle
        )

    }
    NotificationDialog1(
        openDialog = openNotification,
        eventViewModel = eventViewModel
    ) {
        openNotification = false
    }
}

