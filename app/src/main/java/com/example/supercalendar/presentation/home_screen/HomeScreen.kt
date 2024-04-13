package com.example.supercalendar.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.supercalendar.constant.Const.Companion.LOADING
import com.example.supercalendar.constant.STATE
import com.example.supercalendar.presentation.HolidayViewModel
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.components.CalendarView
import com.example.supercalendar.presentation.navigation.Screen
import com.google.android.gms.maps.model.LatLng
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    holidayViewModel: HolidayViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    currentLocation: LatLng
) {
    val visibleMonth = homeViewModel.visibleMonthState.value
    val currentMonth = YearMonth.now()

    var locationName by remember {
        mutableStateOf("未知")
    }

//    LaunchedEffect(key1 = true) {
//        weatherViewModel.getLocationByLatLng(currentLocation)
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = locationName)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
//                        when (weatherViewModel.state) {
//                            STATE.LOADING -> {
//                                locationName = LOADING
//                            }
//                            STATE.SUCCESS -> {
//                                weatherViewModel.geoResponse.location?.let {
//                                    if (it.size > 0) {
//                                        locationName = if (it[0].adm2 == null) LOADING else it[0].adm2!!
//                                    }
//                                }
//                            }
//                            STATE.FAILED -> TODO()
//                        }
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
        ) {
            CalendarView()
        }
    }
}