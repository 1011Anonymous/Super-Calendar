package com.example.supercalendar.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class LocationViewModel: ViewModel() {
    val currentLocation = mutableStateOf(LatLng(0.0, 0.0))
}