package com.example.supercalendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.supercalendar.presentation.WeatherViewModel
import okio.utf8Size

@Composable
fun HourlyComponent(
    modifier: Modifier = Modifier,
    time: String,
    icon: String,
    temperature: String,
) {
    ElevatedCard(
        modifier = modifier.padding(end = 12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.titleSmall,
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://icons.qweather.com/assets/icons/${icon}.svg")
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = "$temperature°",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}