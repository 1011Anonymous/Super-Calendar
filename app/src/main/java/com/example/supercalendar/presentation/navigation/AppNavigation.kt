package com.example.supercalendar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.home_screen.HomeScreen
import com.example.supercalendar.presentation.setting_screen.SettingScreen

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.name
    ) {
        composable(Screen.HomeScreen.name) {
            HomeScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(Screen.SettingScreen.name) {
            SettingScreen(homeViewModel = homeViewModel)
        }
    }
}