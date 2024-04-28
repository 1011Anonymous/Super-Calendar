package com.example.supercalendar.presentation.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.supercalendar.R
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.components.DeleteDialog
import com.example.supercalendar.utils.DateUtils
import com.example.supercalendar.utils.TimeUtils
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetail(
    id: Int,
    eventViewModel: EventViewModel,
    onBack: () -> Unit,
    onUpdate: (id: Int) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    var startTime: LocalTime = LocalTime.now()
    var departure = ""
    var arrive = ""

    eventViewModel.eventForUpdate.startTime?.let {
        startTime = it
    }

    eventViewModel.eventForUpdate.departurePlace?.let {
        departure = it
    }

    eventViewModel.eventForUpdate.arrivePlace?.let {
        arrive = it
    }

    LaunchedEffect(key1 = true) {
        eventViewModel.getEventById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "出行详情") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Spacer(modifier = Modifier.width(60.dp))
                    IconButton(onClick = {
                        openDialog = true
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(160.dp))
                    IconButton(onClick = { onUpdate(id) }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(paddingValues),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                            .size(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_card_travel_24),
                            contentDescription = null
                        )
                    }
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = eventViewModel.eventForUpdate.description,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${DateUtils.dateToString(eventViewModel.eventForUpdate.startDate)} ${
                                TimeUtils.convertLocalTimeToString(
                                    startTime
                                )
                            }",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    thickness = 2.dp
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_notifications_active_24),
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "提醒",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = eventViewModel.eventForUpdate.advance,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 2.dp
                )

                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_location_on_24),
                        contentDescription = null
                    )
                    Text(
                        text = "出发地",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = departure,
                        modifier = Modifier.padding(end = 10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Divider(
                    modifier = Modifier.padding(bottom = 20.dp),
                    thickness = 2.dp
                )

                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_location_on_24),
                        contentDescription = null
                    )
                    Text(
                        text = "到达地",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = arrive,
                        modifier = Modifier.padding(end = 10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        DeleteDialog(
            openDialog = openDialog, id = id,
            eventViewModel = eventViewModel,
            onClose = { openDialog = false },
            onBack = onBack
        )
    }
}