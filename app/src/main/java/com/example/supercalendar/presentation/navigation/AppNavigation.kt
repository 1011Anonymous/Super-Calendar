package com.example.supercalendar.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.home_screen.HomeScreen
import com.example.supercalendar.presentation.setting_screen.SettingScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel,
    currentLocation: LatLng
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.name
    ) {
        composable(Screen.HomeScreen.name) {
            HomeScreen(homeViewModel = homeViewModel, navController = navController, currentLocation = currentLocation)
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
                homeViewModel = homeViewModel,
                onBack = { navController.popBackStack() },
                onContent = {},
                onWeekStart = {},
                onHighlightWeekends = {}
            )
        }
    }
}