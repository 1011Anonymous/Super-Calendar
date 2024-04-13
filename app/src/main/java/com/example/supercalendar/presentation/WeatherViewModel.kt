package com.example.supercalendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.constant.STATE
import com.example.supercalendar.domain.model.geo.GeoInfo
import com.example.supercalendar.retrofit_client.GeoRetrofitClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    var state by mutableStateOf(STATE.LOADING)
    var geoResponse: GeoInfo by mutableStateOf(GeoInfo())
    var errorMessage: String by mutableStateOf("")

    fun getLocationByLatLng(latLng: LatLng) {
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = GeoRetrofitClient.getInstance()
            val latLngToString = String.format("%.2f", latLng.longitude) + "," + String.format("%.2f", latLng.latitude)
            try {
                val apiResponse = apiService.getLocation(latLngToString)
                geoResponse = apiResponse
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }
}