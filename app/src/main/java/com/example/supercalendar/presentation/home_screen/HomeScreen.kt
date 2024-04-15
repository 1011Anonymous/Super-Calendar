package com.example.supercalendar.presentation.home_screen

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.LocationViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.components.CalendarView
import com.example.supercalendar.presentation.components.WeatherCard
import com.example.supercalendar.presentation.navigation.Screen
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    weatherViewModel: WeatherViewModel,
    homeViewModel: HomeViewModel,
    locationViewModel: LocationViewModel,
    navController: NavController,
    locationPermissionRequest: ActivityResultLauncher<Array<String>>
) {
    val visibleMonth = homeViewModel.visibleMonthState.value
    val currentMonth = YearMonth.now()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
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
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Events")
            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            CalendarView(homeViewModel = homeViewModel)
            
            Spacer(modifier = Modifier.height(10.dp))
            
//            Text(text = String.format("%.2f", locationViewModel.currentLocation.value.longitude))
//            Text(text = String.format("%.2f", locationViewModel.currentLocation.value.latitude))
            if (!homeViewModel.hideWeather) {
                WeatherCard(navController = navController, weatherViewModel = weatherViewModel)
            }
        }
    }
}