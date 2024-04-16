package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.ui.theme.bigTitleTextStyle

@Composable
fun ContentDialog(
    openDialog: Boolean,
    onClose: () -> Unit,
    homeViewModel: HomeViewModel
) {

    if (openDialog) {
        AlertDialog(

            title = {
                Text(
                    text = "日历内容显示",
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { homeViewModel.updateDisplayHoliday() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                    ) {
                        Text(
                            text = "显示假日",
                            color = Color.Black,
                            style = bigTitleTextStyle
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Switch(
                            modifier = Modifier.size(width = 12.dp, height = 8.dp),
                            checked = homeViewModel.displayHoliday,
                            onCheckedChange = { homeViewModel.displayHoliday = it }
                        )
                    }

                    TextButton(
                        onClick = { homeViewModel.updateDisplayFestival() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                    ) {
                        Text(
                            text = "显示节日",
                            color = Color.Black,
                            style = bigTitleTextStyle
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Switch(
                            modifier = Modifier.size(width = 12.dp, height = 8.dp),
                            checked = homeViewModel.displayFestival,
                            onCheckedChange = { homeViewModel.displayFestival = it }
                        )
                    }

                    TextButton(
                        onClick = { homeViewModel.updateDisplayLunar() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                    ) {
                        Text(
                            text = "显示农历",
                            color = Color.Black,
                            style = bigTitleTextStyle
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Switch(
                            modifier = Modifier.size(width = 12.dp, height = 8.dp),
                            checked = homeViewModel.displayLunar,
                            onCheckedChange = { homeViewModel.displayLunar = it }
                        )
                    }

                    TextButton(
                        onClick = { homeViewModel.updateDisplayWeek() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                    ) {
                        Text(
                            text = "显示周数",
                            color = Color.Black,
                            style = bigTitleTextStyle
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Switch(
                            modifier = Modifier.size(width = 12.dp, height = 8.dp),
                            checked = homeViewModel.displayDayOfWeek,
                            onCheckedChange = { homeViewModel.displayDayOfWeek = it }
                        )
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
                TextButton(onClick = {
                    onClose()
                }) {
                    Text(text = "保存")
                }
            }

        )
    }
}