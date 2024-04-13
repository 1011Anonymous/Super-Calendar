package com.example.supercalendar

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.supercalendar.presentation.LocationViewModel
import com.example.supercalendar.presentation.navigation.AppNavigation
import com.example.supercalendar.ui.theme.SuperCalendarTheme
import com.example.supercalendar.utils.LocationManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationViewModel: LocationViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                LocationManager.Builder
                    .create(this@MainActivity)
                    .request(onUpdateLocation = { latitude: Double, longitude: Double ->
                        LocationManager.removeCallBack(this@MainActivity)

                        locationViewModel.currentLocation.value = LatLng(latitude, longitude)
                    })
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                LocationManager.Builder
                    .create(this@MainActivity)
                    .request(onUpdateLocation = { latitude: Double, longitude: Double ->
                        LocationManager.removeCallBack(this@MainActivity)

                        locationViewModel.currentLocation.value = LatLng(latitude, longitude)
                    })
            }

            else -> {
                LocationManager.goSettingScreen(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(key1 = Unit) {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

            SuperCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        locationViewModel = locationViewModel,
                        locationPermissionRequest = locationPermissionRequest
                    )
                }
            }
        }
    }

}


