package com.example.supercalendar.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.supercalendar.constant.Const.Companion.NA
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.navigation.Screen
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherCard(
    navController: NavController,
    locationName: String
) {
    var currentTemp = NA
    var tempMin1 = NA
    var tempMax1 = NA
    var tempMin2 = ""
    var tempMax2 = ""
    var tempMin3 = ""
    var tempMax3 = ""
    var description1 = NA
    var description2 = NA
    var description3 = NA
    var windDir1 = NA
    var windDir2 = ""
    var windDir3 = ""
    var windScale1 = NA
    var windScale2 = ""
    var windScale3 = ""
    var humidity = NA
    var iconID1 = ""
    var iconID2 = ""
    var aqi = ""
    var category = ""

    /*
    if (!weatherResponse.now?.temp.isNullOrEmpty()) {
        weatherResponse.now?.temp?.let {
            currentTemp = it
        }
    }

    forecastResponse.daily.let {
        if (it!!.size > 0) {
            tempMin1 = if (it[0].tempMin == null) Const.LOADING else it[0].tempMin!!
            tempMax1 = if (it[0].tempMax == null) Const.LOADING else it[0].tempMax!!
            tempMin2 = if (it[1].tempMin == null) Const.LOADING else it[1].tempMin!!
            tempMax2 = if (it[1].tempMax == null) Const.LOADING else it[1].tempMax!!
            tempMin3 = if (it[2].tempMin == null) Const.LOADING else it[2].tempMin!!
            tempMax3 = if (it[2].tempMax == null) Const.LOADING else it[2].tempMax!!
            description2 = if (it[1].textDay == null) Const.LOADING else it[1].textDay!!
            description3 = if (it[2].textDay == null) Const.LOADING else it[2].textDay!!
            windDir2 = if (it[1].windDirDay == null) Const.LOADING else it[1].windDirDay!!
            windDir3 = if (it[2].windDirDay == null) Const.LOADING else it[2].windDirDay!!
            windScale2 = if (it[1].windScaleDay == null) Const.LOADING else it[1].windScaleDay!!
            windScale3 = if (it[2].windScaleDay == null) Const.LOADING else it[2].windScaleDay!!
            iconID1 = if (it[1].iconDay == null) Const.LOADING else it[1].iconDay!!
            iconID2 = if (it[2].iconDay == null) Const.LOADING else it[2].iconDay!!
        }
    }

    if (!weatherResponse.now?.text.isNullOrEmpty()) {
        weatherResponse.now?.text?.let {
            description1 = it
        }
    }

    if (!weatherResponse.now?.windDir.isNullOrEmpty()) {
        weatherResponse.now?.windDir?.let {
            windDir1 = it
        }
    }

    if (!weatherResponse.now?.windScale.isNullOrEmpty()) {
        weatherResponse.now?.windScale?.let {
            windScale1 = it
        }
    }

    if (!weatherResponse.now?.humidity.isNullOrEmpty()) {
        weatherResponse.now?.humidity?.let {
            humidity = it
        }
    }

    if (!airResponse.now?.aqi.isNullOrEmpty()) {
        airResponse.now?.aqi?.let {
            aqi = it
        }
    }
    if (!airResponse.now?.category.isNullOrEmpty()) {
        airResponse.now?.category?.let {
            category = it
        }
    }
    */



    Card(
        onClick = {
            /*navController.navigate()*/
        },
        modifier = Modifier
            .size(width = 390.dp, height = 130.dp)
            .padding(16.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$currentTemp°",
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )

            Column {
                Text(
                    text = "$tempMin1°/$tempMax1°",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    Text(
                        text = description1,
                        modifier = Modifier.padding(end = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "$aqi$category",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.background(Color.Yellow)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column {
                Text(
                    text = locationName,
                    modifier = Modifier.padding(top = 8.dp, start = 50.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$windDir1$windScale1" + "级" + "|" + "湿度$humidity%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            WeatherInfoSegment(
                day = "明天",
                weatherCondition = description2,
                wind = "$windDir2$windScale2" + "级",
                temperature = "$tempMin2°/$tempMax2°",
                iconID = iconID1
            )
            Divider(
                color = Color.White,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(vertical = 4.dp)
            )
            WeatherInfoSegment(
                day = "后天",
                weatherCondition = description3,
                wind = "$windDir3$windScale3" + "级",
                temperature = "$tempMin3°/$tempMax3°",
                iconID = iconID2
            )
        }
    }

}

@Composable
fun WeatherInfoSegment(
    day: String,
    weatherCondition: String,
    wind: String,
    temperature: String,
    iconID: String
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = day,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = weatherCondition,
                modifier = Modifier.padding(end = 40.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            /*
            when (iconID) {
                "100" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.sunny),
                        contentDescription = "晴"
                    )
                }

                "101" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.cloudy),
                        contentDescription = "多云"
                    )
                }

                "102" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.few_clouds),
                        contentDescription = "少云"
                    )
                }

                "103" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.partly_cloudy),
                        contentDescription = "晴间多云"
                    )
                }

                "104" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.overcast),
                        contentDescription = "阴"
                    )
                }

                "300" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.shower_rain),
                        contentDescription = "阵雨"
                    )
                }

                "301" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.heavy_shower_rain),
                        contentDescription = "强阵雨"
                    )
                }

                "302" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.thundershower),
                        contentDescription = "雷阵雨"
                    )
                }

                "303" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.heavy_thunderstorm),
                        contentDescription = "强雷阵雨"
                    )
                }

                "304" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.thundershower_with_hail),
                        contentDescription = "雷阵雨伴有冰雹"
                    )
                }

                "305" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.light_rain),
                        contentDescription = "小雨"
                    )
                }

                "306" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.moderate_rain),
                        contentDescription = "中雨"
                    )
                }

                "307" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.heavy_rain),
                        contentDescription = "大雨"
                    )
                }

                "308" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.extreme_rain),
                        contentDescription = "极端降雨"
                    )
                }

                "309" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.drizzle_rain),
                        contentDescription = "毛毛雨/细雨"
                    )
                }

                "310" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.storm),
                        contentDescription = "暴雨"
                    )
                }

                "311" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.heavy_storm),
                        contentDescription = "大暴雨"
                    )
                }

                "312" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.severe_storm),
                        contentDescription = "特大暴雨"
                    )
                }

                "313" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.freezing_rain),
                        contentDescription = "冻雨"
                    )
                }

                "314" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.light_to_moderate_rain),
                        contentDescription = "小到中雨"
                    )
                }

                "315" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.moderate_to_heavy_rain),
                        contentDescription = "中到大雨"
                    )
                }

                "316" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.heavy_rain_to_storm),
                        contentDescription = "大到暴雨"
                    )
                }

                "317" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.storm_to_heavy_storm),
                        contentDescription = "暴雨到大暴雨"
                    )
                }

                "318" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.heavy_to_severe_storm),
                        contentDescription = "大暴雨到特大暴雨"
                    )
                }

                "399" -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.rain),
                        contentDescription = "雨"
                    )
                }

                else -> {
                    Image(
                        painter = painterResource(id = com.example.wea_cal_note.R.drawable.unknown),
                        contentDescription = "未知"
                    )
                }
            }
            */

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://icons.qweather.com/assets/icons/$iconID.svg")
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = iconID,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                contentScale = ContentScale.FillBounds
            )

        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = wind,
                modifier = Modifier.padding(start = 4.dp, end = 40.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
            Text(
                text = temperature,
                modifier = Modifier.padding(end = 16.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}