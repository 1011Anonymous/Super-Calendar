package com.example.supercalendar.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.supercalendar.domain.model.event.Event
import com.example.supercalendar.presentation.navigation.Screen
import com.example.supercalendar.ui.theme.taskTextStyle
import com.example.supercalendar.utils.DateUtils.Companion.dateToString
import com.example.supercalendar.utils.TimeUtils.Companion.convertLocalTimeToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    event: Event,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = {
            when (event.category) {
                0 -> navController.navigate("${Screen.ReminderDetailScreen.name}/${event.id}")
                else -> Log.e("Navigation Error", "Navigate to wrong ID")
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /*IconButton(
                onClick = { onDone() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
            }*/

            Column(
                modifier = Modifier.padding(start = 8.dp)
                //.weight(8f)
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
                            "${dateToString(event.startDate)} ${convertLocalTimeToString(event.startTime!!)} ~ ${
                                dateToString(
                                    event.endDate!!
                                )
                            } ${convertLocalTimeToString(event.endTime!!)}"

                        2 -> "全天"
                        else -> convertLocalTimeToString(event.startTime!!)
                    },
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }


            /*IconButton(
                onClick = { onUpdate(event.id) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
            }*/


        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    //EventCard()
}