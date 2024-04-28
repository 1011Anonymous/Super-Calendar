package com.example.supercalendar.presentation.update_screen

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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supercalendar.R
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.components.AdvanceDialog1
import com.example.supercalendar.presentation.components.RepeatDialog
import com.example.supercalendar.presentation.components.TimePickerDialog
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskUpdate(
    id: Int,
    eventViewModel: EventViewModel,
    onBack: () -> Unit
) {
    var openNotification by remember {
        mutableStateOf(false)
    }
    var openInterval by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

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

    val desc = eventViewModel.eventForUpdate.description
    val isAllDay = eventViewModel.eventForUpdate.isAllDay!!
    val startDate = eventViewModel.eventForUpdate.startDate
    val startTime = eventViewModel.eventForUpdate.startTime!!
    val endDate = eventViewModel.eventForUpdate.endDate!!
    val endTime = eventViewModel.eventForUpdate.endTime!!
    val advance = eventViewModel.eventForUpdate.advance
    val repeat = eventViewModel.eventForUpdate.repeat!!

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            this.timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        val newValue = DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)
                        eventViewModel.updateStartDate(newValue)
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
                        val newValue = DateUtils.convertMillisToLocalDate(selectedDate.timeInMillis)
                        eventViewModel.updateEndDate(newValue)
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
                        val newValue = TimeUtils.convertMillisToLocalTime(cal.timeInMillis)
                        eventViewModel.updateStartTime(newValue)
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
                        val newValue = TimeUtils.convertMillisToLocalTime(cal.timeInMillis)
                        eventViewModel.updateEndTime(newValue)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "编辑日程") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    TextButton(onClick = {
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
                placeholder = { Text(text = "请输入日程") },
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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_access_time_24),
                    contentDescription = null
                )
                TextButton(onClick = { eventViewModel.updateIsAllDay(!isAllDay) }) {
                    Text(
                        text = "全天",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(240.dp))
                    Switch(
                        checked = isAllDay,
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
                    Text(text = DateUtils.dateToString(startDate))
                }

                Spacer(modifier = Modifier.weight(1f))

                if (!isAllDay) {
                    TextButton(onClick = { showStartTimePicker = true }) {
                        Text(text = TimeUtils.convertLocalTimeToString(startTime))
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
                    Text(text = DateUtils.dateToString(endDate))
                }

                Spacer(modifier = Modifier.weight(1f))

                if (!isAllDay) {
                    TextButton(onClick = { showEndTimePicker = true }) {
                        Text(text = TimeUtils.convertLocalTimeToString(endTime))
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
                onClick = { openNotification = true }
            ) {
                Text(text = advance)
                Spacer(modifier = Modifier.width(260.dp))
            }
            Divider(modifier = Modifier.padding(bottom = 20.dp))

            Icon(
                painter = painterResource(id = R.drawable.outline_event_repeat_24),
                contentDescription = null
            )

            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openInterval = true }
            ) {
                Text(text = repeat)
                Spacer(modifier = Modifier.width(270.dp))
            }
        }
    }
    AdvanceDialog1(openDialog = openNotification, id = id, eventViewModel = eventViewModel) {
        openNotification = false
    }

    RepeatDialog(openDialog = openInterval, id = id, eventViewModel = eventViewModel) {
        openInterval = false
    }
}