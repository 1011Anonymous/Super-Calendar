package com.example.supercalendar.presentation.setting_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.components.SearchLocationDialog
import com.example.supercalendar.ui.theme.bigTitleTextStyle
import com.example.supercalendar.ui.theme.smallTitleTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel,
    onBack: () -> Unit,
    onContent: () -> Unit,
    onWeekStart: () -> Unit,
    onHighlightWeekends: () -> Unit,
) {
    val highlightWeekendsState = remember {
        mutableStateOf(false)
    }

    val notificationShakeState = remember {
        mutableStateOf(false)
    }

    var openDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "设置",
                        style = TextStyle(fontSize = 26.sp, fontFamily = FontFamily.Monospace)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "通用",
                style = smallTitleTextStyle,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.size(8.dp))

            TextButton(
                onClick = onWeekStart,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 270.dp)
            ) {
                Column {
                    Text(
                        text = "主题背景",
                        color = Color.Black,
                        style = bigTitleTextStyle
                    )

                    Text(
                        text = "浅色",
                        color = Color.Gray,
                        style = smallTitleTextStyle
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "日历",
                style = smallTitleTextStyle,
                color = MaterialTheme.colorScheme.inversePrimary
            )

            TextButton(
                onClick = onContent,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 225.dp)
            ) {
                Text(
                    text = "日历内容显示",
                    color = Color.Black,
                    style = bigTitleTextStyle
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

            TextButton(
                onClick = onWeekStart,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 245.dp)
            ) {
                Column {
                    Text(
                        text = "每周起始日",
                        color = Color.Black,
                        style = bigTitleTextStyle
                    )
                    Text(
                        text = "周一",
                        color = Color.Gray,
                        style = smallTitleTextStyle
                    )
                }
            }


            TextButton(
                onClick = { highlightWeekendsState.value = !highlightWeekendsState.value },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                //contentPadding = PaddingValues(start = 0.dp, end = 215.dp)
            ) {
                Text(
                    text = "高亮周末",
                    color = Color.Black,
                    style = bigTitleTextStyle
                )
                Spacer(modifier = Modifier.width(210.dp))
                Switch(
                    checked = highlightWeekendsState.value,
                    onCheckedChange = { highlightWeekendsState.value = it }
                )
            }
            Spacer(modifier = Modifier.size(8.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                text = "提醒",
                style = smallTitleTextStyle,
                color = MaterialTheme.colorScheme.inversePrimary
            )

            TextButton(
                onClick = onWeekStart,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 245.dp)
            ) {
                Text(
                    text = "自定义通知",
                    color = Color.Black,
                    style = bigTitleTextStyle
                )
            }
            Spacer(modifier = Modifier.size(10.dp))

            TextButton(
                onClick = onWeekStart,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 262.dp)
            ) {
                Column {
                    Text(
                        text = "提醒方式",
                        color = Color.Black,
                        style = bigTitleTextStyle
                    )

                    Text(
                        text = "只通知栏提醒",
                        color = Color.Gray,
                        style = smallTitleTextStyle
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))

            TextButton(
                onClick = onWeekStart,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 262.dp)
            ) {
                Column {
                    Text(
                        text = "提醒铃声",
                        color = Color.Black,
                        style = bigTitleTextStyle
                    )

                    Text(
                        text = "默认铃声",
                        color = Color.Gray,
                        style = smallTitleTextStyle
                    )
                }
            }

            TextButton(
                onClick = { notificationShakeState.value = !notificationShakeState.value },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                //contentPadding = PaddingValues(start = 0.dp, end = 215.dp)
            ) {
                Text(
                    text = "提醒震动",
                    color = Color.Black,
                    style = bigTitleTextStyle
                )
                Spacer(modifier = Modifier.width(210.dp))
                Switch(
                    checked = notificationShakeState.value,
                    onCheckedChange = { notificationShakeState.value = it }
                )
            }
            Spacer(modifier = Modifier.size(8.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "天气",
                style = smallTitleTextStyle,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.size(8.dp))

            TextButton(
                onClick = onWeekStart,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 262.dp)
            ) {
                Text(
                    text = "单位管理",
                    color = Color.Black,
                    style = bigTitleTextStyle
                )
            }
            Spacer(modifier = Modifier.size(15.dp))

            TextButton(
                onClick = { openDialog = true },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(start = 0.dp, end = 262.dp)
            ) {
                Column {
                    Text(
                        text = "地理管理",
                        color = Color.Black,
                        style = bigTitleTextStyle
                    )

                    Text(
                        text = weatherViewModel.locationName,
                        color = Color.Gray,
                        style = smallTitleTextStyle
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
        }

        SearchLocationDialog(
            openDialog = openDialog,
            onClose = { openDialog = false },
            weatherViewModel = weatherViewModel
        )
    }
}

