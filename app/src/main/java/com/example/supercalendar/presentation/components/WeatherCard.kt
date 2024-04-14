package com.example.supercalendar.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.supercalendar.constant.STATE
import com.example.supercalendar.presentation.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherCard(
    navController: NavController,
    weatherViewModel: WeatherViewModel
) {

    val airColor = when (weatherViewModel.category) {
        "优" -> Color(0, 228, 0)
        "良" -> Color(255, 255, 0)
        "轻度污染" -> Color(255, 126, 0)
        "中度污染" -> Color(255, 0, 0)
        "重度污染" -> Color(153, 0, 76)
        "严重污染" -> Color(126, 0, 35)
        else -> Color.Gray
    }

    Card(
        onClick = {
            /*navController.navigate()*/
        },
        modifier = Modifier
            .size(width = 390.dp, height = 130.dp)
            .padding(16.dp),

        ) {
        if (weatherViewModel.state == STATE.LOADING || weatherViewModel.state == STATE.FAILED) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${weatherViewModel.currentTemp}°",
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                Column {
                    Text(
                        text = "${weatherViewModel.tempMin0}°/${weatherViewModel.tempMax0}°",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        Text(
                            text = weatherViewModel.currentText,
                            modifier = Modifier.padding(end = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${weatherViewModel.aqi}${weatherViewModel.category}",
                            color = Color.Black,
                            fontSize = 12.sp,
                            modifier = Modifier.background(airColor)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    Text(
                        text = weatherViewModel.locationName,
                        modifier = Modifier.padding(top = 8.dp, start = 50.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${weatherViewModel.currentWindDir}${weatherViewModel.currentWindScale}" + "级" + "|" + "湿度${weatherViewModel.currentHumidity}%",
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
                    weatherCondition = weatherViewModel.text1,
                    wind = "${weatherViewModel.windDir1}${weatherViewModel.windScale1}" + "级",
                    temperature = "${weatherViewModel.tempMin1}°/${weatherViewModel.tempMax1}°",
                    iconID = weatherViewModel.icon1
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
                    weatherCondition = weatherViewModel.text2,
                    wind = "${weatherViewModel.windDir2}${weatherViewModel.windScale2}" + "级",
                    temperature = "${weatherViewModel.tempMin2}°/${weatherViewModel.tempMax2}°",
                    iconID = weatherViewModel.icon2
                )
            }
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
                modifier = Modifier.padding(start = 4.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.width(44.dp))
            Text(
                text = temperature,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}