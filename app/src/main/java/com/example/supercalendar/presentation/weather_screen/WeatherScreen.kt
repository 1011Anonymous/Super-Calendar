package com.example.supercalendar.presentation.weather_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.supercalendar.R
import com.example.supercalendar.constant.STATE
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.components.ForecastComponent
import com.example.supercalendar.presentation.components.HourlyComponent
import com.example.supercalendar.presentation.components.WeatherComponent
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "天气详情",
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (weatherViewModel.state == STATE.FAILED || weatherViewModel.state == STATE.LOADING) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = weatherViewModel.locationName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${LocalDate.now().monthValue}月${LocalDate.now().dayOfMonth}日",
                    style = MaterialTheme.typography.bodyLarge
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://icons.qweather.com/assets/icons/${weatherViewModel.currentIcon}.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = "${weatherViewModel.currentTemp}°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    text = weatherViewModel.currentText,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    //modifier = Modifier.padding(bottom = 4.dp),
                    text = "体感温度 ${weatherViewModel.feelsLike}°C",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "PM2.5 ${weatherViewModel.pm25}",
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sunrise),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "${weatherViewModel.sunrise0} am",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_sunset),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "${weatherViewModel.sunset0} pm",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                ) {
                    WeatherComponent(
                        modifier = Modifier.weight(1f),
                        weatherLabel = "风速",
                        weatherValue = weatherViewModel.currentWindSpeed,
                        weatherUnit = "km/h",
                        iconId = R.drawable.ic_wind,
                    )
                    WeatherComponent(
                        modifier = Modifier.weight(1f),
                        weatherLabel = "紫外线",
                        weatherValue = weatherViewModel.uvIndex0,
                        weatherUnit = "index",
                        iconId = R.drawable.ic_uv,
                    )
                    WeatherComponent(
                        modifier = Modifier.weight(1f),
                        weatherLabel = "湿度",
                        weatherValue = weatherViewModel.currentHumidity,
                        weatherUnit = "百分比 %",
                        iconId = R.drawable.ic_humidity,
                    )
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "24小时预报",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp),
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(top = 8.dp, start = 16.dp),
                ) {
                    weatherViewModel.forecastHourlyResponse.hourly?.let { hourlyList ->
                        if (hourlyList.isNotEmpty()) {
                            items(hourlyList) { hourly ->
                                var time = ""
                                var icon = ""
                                var temp = ""

                                hourly.fxTime?.let { time = it.substring(11, 16) }
                                hourly.icon?.let { icon = it }
                                hourly.temp?.let { temp = it }

                                HourlyComponent(
                                    time = time,
                                    icon = icon,
                                    temperature = temp
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "七天预报",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp),
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(top = 8.dp, start = 16.dp),
                ) {
                    weatherViewModel.forecastDailyResponse.daily?.let { dailyList ->
                        if (dailyList.isNotEmpty()) {
                            items(dailyList) { daily ->
                                var date = ""
                                var icon = ""
                                var minTemp = ""
                                var maxTemp = ""

                                daily.fxDate?.let { date = it.substring(5, 10) }
                                daily.iconDay?.let { icon = it }
                                daily.tempMin?.let { minTemp = it }
                                daily.tempMax?.let { maxTemp = it }

                                ForecastComponent(
                                    date = date,
                                    icon = icon,
                                    minTemp = minTemp,
                                    maxTemp = maxTemp
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))

            }
        }
    }
}