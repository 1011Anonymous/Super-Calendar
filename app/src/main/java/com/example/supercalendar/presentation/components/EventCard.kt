package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supercalendar.domain.model.event.Event
import com.example.supercalendar.ui.theme.taskTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    event: Event,
    onDone: () -> Unit,
    onUpdate: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = {  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*
            IconButton(
                onClick = { onDone() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
            }
            */
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
                        0 -> event.startTime!!
                        1 -> "${event.startDate} ${event.startTime} ~ ${event.endDate} ${event.endTime}"
                        2 -> "全天"
                        else -> "${event.startDate} ${event.startTime}"
                    },
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            /*
            IconButton(
                onClick = { onUpdate(event.id) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
            }
            */

        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    //EventCard()
}