package com.example.supercalendar.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.constant.Const.Companion.UNKNOWN
import com.example.supercalendar.constant.STATE
import com.example.supercalendar.domain.model.geo.GeoInfo
import com.example.supercalendar.retrofit_client.GeoRetrofitClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    var state by mutableStateOf(STATE.LOADING)
    var geoResponse: GeoInfo by mutableStateOf(GeoInfo())
    var errorMessage: String by mutableStateOf("")

    var locationName by mutableStateOf(UNKNOWN)
    var locationId by mutableStateOf("")

    fun getLocationByLatLng(latLng: LatLng) {
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = GeoRetrofitClient.getInstance()
            val latLngToString = String.format("%.2f", latLng.longitude) + "," + String.format(
                "%.2f",
                latLng.latitude
            )
            try {
                val apiResponse = apiService.getLocation(latLngToString)
                geoResponse = apiResponse
                geoResponse.location?.let { locationArrayList ->
                    if (locationArrayList.size > 0) {
                        locationArrayList[0].adm2?.let { locationName = it }
                        locationArrayList[0].id?.let { locationId = it }
                    }
                }
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
            Log.d("LocationName", "LocationName: $locationName")
            Log.d("LocationID", "LocationId: $locationId")
        }
    }

    fun getLocationByName(name: String) {
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = GeoRetrofitClient.getInstance()
            Log.d("LocationID", "LocationId: $locationId")
            try {
                val apiResponse = apiService.getLocation(name)
                geoResponse = apiResponse
                geoResponse.location?.let { locationArrayList ->
                    if (locationArrayList.size > 0) {
                        locationArrayList[0].id?.let { locationId = it }
                    }
                }
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }


}