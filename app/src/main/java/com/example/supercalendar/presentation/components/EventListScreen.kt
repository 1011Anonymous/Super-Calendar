package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.ui.theme.bigTitleTextStyle
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.ui.theme.topAppBarTextStyle
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    eventViewModel: EventViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "事件纵览",
                        style = topAppBarTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        val allEvents by eventViewModel.getAllEvents.collectAsState(initial = emptyList())

        if (allEvents.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "还没有创建事件！",
                    style = bigTitleTextStyle
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allEvents) {event ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = when (event.category) {
                                        3 -> "${event.description}: ${event.departurePlace} -> ${event.arrivePlace}"
                                        2 -> "${event.description}的生日"
                                        else -> event.description
                                    },
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = taskTextStyle
                                )

                                Text(
                                    text = when (event.category) {
                                        1 -> if (event.isAllDay == true) "全天" else
                                            "${DateUtils.dateToString(event.startDate)} ${TimeUtils.convertLocalTimeToString(event.startTime!!)} ~ ${
                                                DateUtils.dateToString(
                                                    event.endDate!!
                                                )
                                            } ${TimeUtils.convertLocalTimeToString(event.endTime!!)}"

                                        2 -> "全天"
                                        else -> TimeUtils.convertLocalTimeToString(event.startTime!!)
                                    },
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

