package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val selectedIsHoliday by homeViewModel.displayHoliday.collectAsState(initial = false)
    var isHoliday = selectedIsHoliday

    val selectedIsLunar by homeViewModel.displayLunar.collectAsState(initial = false)
    var isLunar = selectedIsLunar

    val selectedIsFestival by homeViewModel.displayFestival.collectAsState(initial = false)
    var isFestival = selectedIsFestival

    val selectedIsWeekday by homeViewModel.displayWeekday.collectAsState(initial = false)
    var isWeekday = selectedIsWeekday

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
                        onClick = {
                            isHoliday = !isHoliday
                            homeViewModel.updateDisplayHoliday(isHoliday)
                        },
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
                            checked = selectedIsHoliday,
                            onCheckedChange = {
                                isHoliday = !isHoliday
                                homeViewModel.updateDisplayHoliday(isHoliday)
                            }
                        )
                    }

                    TextButton(
                        onClick = {
                            isFestival = !isFestival
                            homeViewModel.updateDisplayFestival(isFestival)
                        },
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
                            checked = selectedIsFestival,
                            onCheckedChange = {
                                isFestival = !isFestival
                                homeViewModel.updateDisplayFestival(isFestival)
                            }
                        )
                    }

                    TextButton(
                        onClick = {
                            isLunar = !isLunar
                            homeViewModel.updateDisplayLunar(isLunar)
                        },
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
                            checked = selectedIsLunar,
                            onCheckedChange = {
                                isLunar = !isLunar
                                homeViewModel.updateDisplayLunar(isLunar)
                            }
                        )
                    }

                    TextButton(
                        onClick = {
                            isWeekday = !isWeekday
                            homeViewModel.updateDisplayWeek(isWeekday)
                        },
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
                            checked = selectedIsWeekday,
                            onCheckedChange = {
                                isWeekday = !isWeekday
                                homeViewModel.updateDisplayWeek(isWeekday)
                            }
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