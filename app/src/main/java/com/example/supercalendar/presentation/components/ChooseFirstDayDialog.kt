package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.supercalendar.presentation.HomeViewModel

@Composable
fun ChooseFirstDayDialog(
    homeViewModel: HomeViewModel,
    onClose: () -> Unit,
    open: Boolean
) {
    val options = listOf("周六", "周日", "周一")
    var selectedOption by remember {
        mutableStateOf(options[0])
    }

    if (open) {
        AlertDialog(
            title = {
                Text(
                    text = "每周起始日",
                    fontFamily = FontFamily.Serif
                )
            },
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
                                        homeViewModel.updateFirstDayOfWeek(option)
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
            dismissButton = {
                TextButton(onClick = {
                    onClose()
                }) {
                    Text(text = "取消")
                }
            },
            confirmButton = {

            }
        )
    }
}