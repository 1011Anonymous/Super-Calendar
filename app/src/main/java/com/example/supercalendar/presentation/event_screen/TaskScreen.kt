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
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import com.example.supercalendar.R
import com.example.supercalendar.constant.Const
import com.example.supercalendar.presentation.components.TimePickerDialog
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.convertMillisToLocalTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        var text by remember {
            mutableStateOf("")
        }
        val focusRequester = remember {
            FocusRequester()
        }
        val focusManager = LocalFocusManager.current

        val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

        var isChecked by remember {
            mutableStateOf(false)
        }
        val datePickerState = rememberDatePickerState()
        var showStartDatePicker by remember {
            mutableStateOf(false)
        }
        var showEndDatePicker by remember {
            mutableStateOf(false)
        }

        val timePickerState = rememberTimePickerState(is24Hour = true)
        var showStartTimePicker by remember {
            mutableStateOf(false)
        }
        var showEndTimePicker by remember {
            mutableStateOf(false)
        }

        var startDateText by remember {
            mutableStateOf(
                "${LocalDate.now().year}年" +
                        "${LocalDate.now().monthValue}月" +
                        "${LocalDate.now().dayOfMonth}日" +
                        Const.chineseNumerals[LocalDate.now().dayOfWeek.value]
            )
        }
        var endDateText by remember {
            mutableStateOf(
                "${LocalDate.now().year}年" +
                        "${LocalDate.now().monthValue}月" +
                        "${LocalDate.now().dayOfMonth}日" +
                        Const.chineseNumerals[LocalDate.now().dayOfWeek.value]
            )
        }


        val startTime = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()).format(LocalTime.now())
        val endTime = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()).format(LocalTime.now())
        var startTimeText by remember {
            mutableStateOf(startTime)
        }
        var endTimeText by remember {
            mutableStateOf(endTime)
        }


        if (showStartDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDate = Calendar.getInstance().apply {
                                this.timeInMillis = datePickerState.selectedDateMillis!!
                            }
                            val localDate =
                                DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)
                            startDateText = DateUtils.dateToString(localDate)
                            showStartDatePicker = false
                        }
                    ) { Text("确定") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showStartDatePicker = false
                        }
                    ) { Text("取消") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showEndDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDate = Calendar.getInstance().apply {
                                this.timeInMillis = datePickerState.selectedDateMillis!!
                            }
                            val localDate =
                                DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)
                            endDateText = DateUtils.dateToString(localDate)
                            showEndDatePicker = false
                        }
                    ) { Text("确定") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showEndDatePicker = false
                        }
                    ) { Text("取消") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showStartTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showStartTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val cal = Calendar.getInstance()
                            cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            cal.set(Calendar.MINUTE, timePickerState.minute)
                            cal.isLenient = false
                            startTimeText =  DateTimeFormatter
                                .ofPattern("HH:mm", Locale.getDefault())
                                .format(convertMillisToLocalTime(cal.timeInMillis))
                            showStartTimePicker = false
                        }
                    ) { Text("确定") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showStartTimePicker = false
                        }
                    ) { Text("取消") }
                }
            ) {
                TimePicker(state = timePickerState)
            }
        }

        if (showEndTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showEndTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val cal = Calendar.getInstance()
                            cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            cal.set(Calendar.MINUTE, timePickerState.minute)
                            cal.isLenient = false
                            endTimeText = DateTimeFormatter
                                .ofPattern("HH:mm", Locale.getDefault())
                                .format(convertMillisToLocalTime(cal.timeInMillis))
                            showEndTimePicker = false
                        }
                    ) { Text("确定") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showEndTimePicker = false
                        }
                    ) { Text("取消") }
                }
            ) {
                TimePicker(state = timePickerState)
            }
        }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "请输入日程") },
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

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_access_time_24),
                contentDescription = null
            )
            TextButton(onClick = { isChecked = !isChecked }) {
                Text(
                    text = "全天",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(240.dp))
                Switch(
                    checked = isChecked,
                    onCheckedChange = null
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { showStartDatePicker = true }) {
                Text(text = startDateText)
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!isChecked) {
                TextButton(onClick = { showStartTimePicker = true }) {
                    Text(text = startTimeText)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { showEndDatePicker = true }) {
                Text(text = endDateText)
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!isChecked) {
                TextButton(onClick = { showEndTimePicker = true }) {
                    Text(text = endTimeText)
                }
            }
        }
        Divider(modifier = Modifier.padding(bottom = 20.dp))

        Icon(
            painter = painterResource(id = R.drawable.outline_notifications_active_24),
            contentDescription = null
        )

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "任务发生时")
            Spacer(modifier = Modifier.width(260.dp))
        }
        Divider(modifier = Modifier.padding(bottom = 20.dp))

        Icon(imageVector = Icons.Outlined.Refresh, contentDescription = null)

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "不重复")
            Spacer(modifier = Modifier.width(270.dp))
        }
    }

}

@Preview
@Composable
fun PreviewTask() {
    TaskScreen()
}