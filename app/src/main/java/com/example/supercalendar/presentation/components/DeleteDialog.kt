package com.example.supercalendar.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.supercalendar.presentation.EventViewModel

@Composable
fun DeleteDialog(
    openDialog: Boolean,
    id: Int,
    eventViewModel: EventViewModel,
    onClose: () -> Unit,
    onBack: () -> Unit
) {
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

