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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.supercalendar.R
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.components.AdvanceDialog1
import com.example.supercalendar.presentation.components.TimePickerDialog
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelUpdate(
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

    val timePickerState = rememberTimePickerState(is24Hour = true)
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    val desc = eventViewModel.eventForUpdate.description
    val date = eventViewModel.eventForUpdate.startDate
    val time = eventViewModel.eventForUpdate.startTime!!
    val advance = eventViewModel.eventForUpdate.advance
    val departure = eventViewModel.eventForUpdate.departurePlace!!
    val arrive = eventViewModel.eventForUpdate.arrivePlace!!

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
                        val newValue = TimeUtils.convertMillisToLocalTime(cal.timeInMillis)
                        eventViewModel.updateStartTime(newValue)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "编辑出行") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
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
                placeholder = { Text(text = "请输入航班或车次号") },
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

            Icon(
                painter = painterResource(id = R.drawable.outline_access_time_24),
                contentDescription = null
            )

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
                onClick = { openDialog = true }
            ) {
                Text(text = advance)
                Spacer(modifier = Modifier.width(260.dp))
            }
            Divider(modifier = Modifier.padding(bottom = 20.dp))

            OutlinedTextField(
                value = departure,
                onValueChange = { newValue ->
                    eventViewModel.updateDeparture(newValue)
                },
                label = { Text(text = "出发地") },
                placeholder = { Text(text = "请输入提醒") },
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
                    IconButton(onClick = { eventViewModel.updateDeparture("") }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = null
                        )
                    }
                },
                textStyle = taskTextStyle
            )
            Divider(modifier = Modifier.padding(bottom = 20.dp))

            OutlinedTextField(
                value = arrive,
                onValueChange = { newValue ->
                    eventViewModel.updateArrive(newValue)
                },
                placeholder = { Text(text = "请输入提醒") },
                label = { Text(text = "到达地") },
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
                    IconButton(onClick = { eventViewModel.updateArrive("") }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = null
                        )
                    }
                },
                textStyle = taskTextStyle
            )
        }
    }
    AdvanceDialog1(openDialog = openDialog, id = id, eventViewModel = eventViewModel) {
        openDialog = false
    }
}