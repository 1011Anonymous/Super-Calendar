package com.example.supercalendar

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.LocationViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.navigation.AppNavigation
import com.example.supercalendar.ui.theme.SuperCalendarTheme
import com.example.supercalendar.utils.LocationManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationViewModel: LocationViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    private val eventViewModel: EventViewModel by viewModels<EventViewModel>()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION,
                false
            ) || permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                LocationManager.Builder
                    .create(this@MainActivity)
                    .request(onUpdateLocation = { latitude: Double, longitude: Double ->
                        LocationManager.removeCallBack(this@MainActivity)

                        locationViewModel.currentLocation.value = LatLng(latitude, longitude)

                        weatherViewModel.getLocationByLatLng(
                            latLng = locationViewModel.currentLocation.value,
                        )


                    })
            }

            else -> {
                LocationManager.goSettingScreen(this)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val theme by homeViewModel.darkTheme.collectAsState(initial = "跟随系统")
            val isDark = when (theme) {
                "浅色" -> false
                "深色" -> true
                else -> isSystemInDarkTheme()
            }

            LaunchedEffect(key1 = Unit) {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

            SuperCalendarTheme(
                darkTheme = isDark
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        eventViewModel = eventViewModel,
                        homeViewModel = homeViewModel,
                        locationViewModel = locationViewModel,
                        weatherViewModel = weatherViewModel,
                        locationPermissionRequest = locationPermissionRequest
                    )
                }
            }
        }
    }

}


