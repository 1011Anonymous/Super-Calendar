package com.example.supercalendar.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supercalendar.constant.Const.Companion.LOADING
import com.example.supercalendar.constant.Const.Companion.UNKNOWN
import com.example.supercalendar.constant.STATE
import com.example.supercalendar.domain.model.air.AirInfo
import com.example.supercalendar.domain.model.forecast_daily.ForecastDailyInfo
import com.example.supercalendar.domain.model.forecast_hourly.ForecastHourlyInfo
import com.example.supercalendar.domain.model.geo.GeoInfo
import com.example.supercalendar.domain.model.weather_now.WeatherInfo
import com.example.supercalendar.retrofit_client.AirRetrofitClient
import com.example.supercalendar.retrofit_client.ForecastDailyClient
import com.example.supercalendar.retrofit_client.ForecastHourlyClient
import com.example.supercalendar.retrofit_client.GeoRetrofitClient
import com.example.supercalendar.retrofit_client.WeatherNowClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    var state by mutableStateOf(STATE.LOADING)

    var errorMessage: String by mutableStateOf("")

    var geoResponse: GeoInfo by mutableStateOf(GeoInfo())
    var weatherNowResponse: WeatherInfo by mutableStateOf(WeatherInfo())
    var forecastDailyResponse: ForecastDailyInfo by mutableStateOf(ForecastDailyInfo())
    var forecastHourlyResponse: ForecastHourlyInfo by mutableStateOf(ForecastHourlyInfo())
    var airResponse: AirInfo by mutableStateOf(AirInfo())


    var locationName by mutableStateOf(UNKNOWN)
    var locationId by mutableStateOf("")

    //Now
    var currentTemp by mutableStateOf(LOADING)
    var feelsLike by mutableStateOf(LOADING)
    var currentText by mutableStateOf(LOADING)
    var currentIcon by mutableStateOf(LOADING)
    var currentWindDir by mutableStateOf(LOADING)
    var currentWindScale by mutableStateOf(LOADING)
    var currentWindSpeed by mutableStateOf(LOADING)
    var currentHumidity by mutableStateOf(LOADING)

    //Daily
    var tempMax0 by mutableStateOf(LOADING)
    var tempMax1 by mutableStateOf(LOADING)
    var tempMax2 by mutableStateOf(LOADING)
    var tempMin0 by mutableStateOf(LOADING)
    var tempMin1 by mutableStateOf(LOADING)
    var tempMin2 by mutableStateOf(LOADING)
    var text1 by mutableStateOf(LOADING)
    var text2 by mutableStateOf(LOADING)
    var icon1 by mutableStateOf(LOADING)
    var icon2 by mutableStateOf(LOADING)
    var windDir1 by mutableStateOf(LOADING)
    var windDir2 by mutableStateOf(LOADING)
    var windScale1 by mutableStateOf(LOADING)
    var windScale2 by mutableStateOf(LOADING)
    var sunrise0 by mutableStateOf(LOADING)
    var sunset0 by mutableStateOf(LOADING)
    var uvIndex0 by mutableStateOf(LOADING)

    //Air
    var aqi by mutableStateOf(LOADING)
    var category by mutableStateOf(LOADING)
    var pm25 by mutableStateOf(LOADING)



    fun getLocationByLatLng(latLng: LatLng, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
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
                        locationArrayList[0].id?.let { locationId = it }
                        locationArrayList[0].adm2?.let { locationName = it }
                        onSuccess()

                    } else {
                        onFailure()
                        state = STATE.FAILED
                    }
                } ?: run {
                    onFailure()
                    state = STATE.FAILED
                }
            } catch (e: Exception) {
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
                onFailure()
            }
            Log.d("LocationName", "LocationName: $locationName")
            Log.d("LocationID", "LocationId: $locationId")
        }
    }

    fun getLocationByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = STATE.LOADING
            val apiService = GeoRetrofitClient.getInstance()
            Log.d("LocationID", "LocationId: $locationId")
            try {
                val apiResponse = apiService.getLocation(name)
                geoResponse = apiResponse
                geoResponse.location?.let { locationArrayList ->
                    if (locationArrayList.size > 0) {
                        locationArrayList[0].id?.let { locationId = it }
                        locationArrayList[0].name?.let { locationName = it }
                        updateAll(locationId)
                    }
                }
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message!!.toString()
                state = STATE.FAILED
            }
        }
    }

    fun getCurrentWeather(locationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = STATE.LOADING
            val apiService = WeatherNowClient.getInstance()

            try {
                weatherNowResponse = apiService.getWeatherNow(locationId)
                weatherNowResponse.now?.let { now ->
                    now.temp?.let { currentTemp = it }
                    now.feelsLike?.let { feelsLike = it }
                    now.text?.let { currentText = it }
                    now.icon?.let { currentIcon = it }
                    now.windDir?.let { currentWindDir = it }
                    now.windScale?.let { currentWindScale = it }
                    now.windSpeed?.let { currentWindSpeed = it }
                    now.humidity?.let { currentHumidity = it }
                }
                weatherNowResponse.code?.let {
                    if (it == "200" || it == "204") state = STATE.SUCCESS
                }
            } catch (e: Exception) {
                Log.d("WeatherNow", e.message!!.toString())
                state = STATE.FAILED
            }
        }
    }

    fun getDailyWeather(locationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = STATE.LOADING
            val apiService = ForecastDailyClient.getInstance()

            try {
                forecastDailyResponse = apiService.getForecastDaily(locationId)
                forecastDailyResponse.daily?.let { dailyArrayList ->
                    if (dailyArrayList.size > 3) {
                        dailyArrayList[0].tempMax?.let { tempMax0 = it }
                        dailyArrayList[0].tempMin?.let { tempMin0 = it }
                        dailyArrayList[0].sunrise?.let { sunrise0 = it }
                        dailyArrayList[0].sunset?.let { sunset0 = it }
                        dailyArrayList[0].uvIndex?.let { uvIndex0 = it }
                        dailyArrayList[1].tempMax?.let { tempMax1 = it }
                        dailyArrayList[1].tempMin?.let { tempMin1 = it }
                        dailyArrayList[1].textDay?.let { text1 = it }
                        dailyArrayList[1].iconDay?.let { icon1 = it }
                        dailyArrayList[1].windDirDay?.let { windDir1 = it }
                        dailyArrayList[1].windScaleDay?.let { windScale1 = it }
                        dailyArrayList[2].tempMax?.let { tempMax2 = it }
                        dailyArrayList[2].tempMin?.let { tempMin2 = it }
                        dailyArrayList[2].textDay?.let { text2 = it }
                        dailyArrayList[2].iconDay?.let { icon2 = it }
                        dailyArrayList[2].windDirDay?.let { windDir2 = it }
                        dailyArrayList[2].windScaleDay?.let { windScale2 = it }
                    }
                }
                forecastDailyResponse.code?.let {
                    if (it == "200" || it == "204") {
                        state = STATE.SUCCESS
                    }
                }
            } catch (e: Exception) {
                Log.d("ForecastDaily", e.message!!.toString())
                state = STATE.FAILED
            }
        }
    }

    fun getHourlyWeather(locationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = STATE.LOADING
            val apiService = ForecastHourlyClient.getInstance()

            try {
                forecastHourlyResponse = apiService.getForecastHourly(locationId)
                forecastHourlyResponse.code?.let {
                    if (it == "200" || it == "204") {
                        state = STATE.SUCCESS
                    }
                }
            } catch (e: Exception) {
                Log.d("ForecastHourly", e.message!!.toString())
                state = STATE.FAILED
            }
        }
    }

    fun getAir(locationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state = STATE.LOADING
            val apiService = AirRetrofitClient.getInstance()

            try {
                airResponse = apiService.getAir(locationId)
                airResponse.now?.let { now ->
                    now.aqi?.let { aqi = it }
                    now.category?.let { category = it }
                    now.pm2p5?.let { pm25 = it }
                }
                airResponse.code?.let {
                    if (it == "200" || it == "204") {
                        state = STATE.SUCCESS
                    }
                }
            } catch (e: Exception) {
                Log.d("Air", e.message!!.toString())
                state = STATE.FAILED
            }
        }
    }

    fun updateAll(locationId: String) {
        getCurrentWeather(locationId)
        getDailyWeather(locationId)
        getHourlyWeather(locationId)
        getAir(locationId)
    }

}