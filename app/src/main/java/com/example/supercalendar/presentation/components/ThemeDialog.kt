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
fun ThemeDialog(
    openDialog: Boolean,
    homeViewModel: HomeViewModel,
    onClose: () -> Unit
) {
    val options = listOf("跟随系统", "深色", "浅色")
    var selectedOption by remember {
        mutableStateOf(options[0])
    }

    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = "选择主题",
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
                                        homeViewModel.updateDarkTheme(selectedOption)
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