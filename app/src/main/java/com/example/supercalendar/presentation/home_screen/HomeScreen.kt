package com.example.supercalendar.presentation.home_screen

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.LocationViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.common.mySnackBar
import com.example.supercalendar.presentation.components.CalendarView
import com.example.supercalendar.presentation.components.EventCard
import com.example.supercalendar.presentation.components.WeatherCard
import com.example.supercalendar.presentation.navigation.Screen
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    weatherViewModel: WeatherViewModel,
    homeViewModel: HomeViewModel,
    eventViewModel: EventViewModel,
    locationViewModel: LocationViewModel,
    navController: NavController,
    locationPermissionRequest: ActivityResultLauncher<Array<String>>,
    onUpdate: (id: Int) -> Unit
) {
    val visibleMonth = homeViewModel.visibleMonthState.value
    val currentMonth = YearMonth.now()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = {

                        locationPermissionRequest.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Fetch weather api",
                        )
                    }
                },
                actions = {
                    if (visibleMonth != currentMonth) {
                        IconButton(onClick = {
                            homeViewModel.resetToCurrentMonth()
                            homeViewModel.setIsGoBackToday(true)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "go back to today"
                            )
                        }
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.List, contentDescription = "Events")
                    }
                    IconButton(onClick = { navController.navigate(Screen.SettingScreen.name) }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "MoreInfo"
                        )
                    }
                },
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = { navController.navigate(Screen.EventScreen.name) }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Events")
            }
        },

        ) { innerPadding ->

        val selectedHideWeather by homeViewModel.hideWeather.collectAsState(initial = false)

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            CalendarView(homeViewModel = homeViewModel, eventViewModel = eventViewModel)
            
            Spacer(modifier = Modifier.height(10.dp))

            if (!selectedHideWeather) {
                WeatherCard(navController = navController, weatherViewModel = weatherViewModel)
            }

            val events by
            eventViewModel.getEventsByDate(homeViewModel.selectedDate).collectAsState(
                initial = emptyList()
            )

            if (events.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = events,
                        key = { item -> item.id }
                    ) {event ->
                        EventCard(
                            event = event,
                            onDone = {
                                eventViewModel.deleteEvent(event)
                                mySnackBar(
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    message = "撤销删除! -> \"${event.description}\"",
                                    actionLabel = "UNDO",
                                    onAction = { eventViewModel.undoDeleteEvent() }
                                )
                            },
                            onUpdate = onUpdate
                        )
                    }
                }
            }

        }
    }
}
