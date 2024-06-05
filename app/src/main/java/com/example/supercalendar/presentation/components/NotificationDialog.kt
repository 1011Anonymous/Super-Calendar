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

