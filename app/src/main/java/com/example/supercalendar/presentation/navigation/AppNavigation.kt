package com.example.supercalendar.presentation.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.LocationViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.home_screen.HomeScreen
import com.example.supercalendar.presentation.setting_screen.SettingScreen
import com.example.supercalendar.presentation.weather_screen.WeatherScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel,
    locationPermissionRequest: ActivityResultLauncher<Array<String>>
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.name
    ) {
        composable(Screen.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                locationViewModel = locationViewModel,
                locationPermissionRequest = locationPermissionRequest,
                weatherViewModel = weatherViewModel
            )
        }
        composable(
            route = Screen.SettingScreen.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) {
            SettingScreen(
                onBack = { navController.popBackStack() },
                onContent = {},
                onWeekStart = {},
                onHighlightWeekends = {}
            )
        }
        composable(route = Screen.WeatherScreen.name) {
            WeatherScreen(weatherViewModel = weatherViewModel)
        }
    }
}