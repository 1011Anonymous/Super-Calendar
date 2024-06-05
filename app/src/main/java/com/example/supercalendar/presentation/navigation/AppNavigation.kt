package com.example.supercalendar.presentation.navigation

import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.supercalendar.presentation.EventViewModel
import com.example.supercalendar.presentation.HomeViewModel
import com.example.supercalendar.presentation.LocationViewModel
import com.example.supercalendar.presentation.WeatherViewModel
import com.example.supercalendar.presentation.components.EventListScreen
import com.example.supercalendar.presentation.detail_screen.BirthdayDetail
import com.example.supercalendar.presentation.detail_screen.ReminderDetail
import com.example.supercalendar.presentation.detail_screen.TaskDetail
import com.example.supercalendar.presentation.detail_screen.TravelDetail
import com.example.supercalendar.presentation.event_screen.EventScreen
import com.example.supercalendar.presentation.home_screen.HomeScreen
import com.example.supercalendar.presentation.setting_screen.SettingScreen
import com.example.supercalendar.presentation.update_screen.BirthdayUpdate
import com.example.supercalendar.presentation.update_screen.ReminderUpdate
import com.example.supercalendar.presentation.update_screen.TaskUpdate
import com.example.supercalendar.presentation.update_screen.TravelUpdate
import com.example.supercalendar.presentation.weather_screen.WeatherScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigation(
    eventViewModel: EventViewModel,
    homeViewModel: HomeViewModel,
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
                weatherViewModel = weatherViewModel,
                homeViewModel = homeViewModel,
                eventViewModel = eventViewModel,
                onClick = { id ->
                    navController.navigate("${Screen.ReminderDetailScreen.name}/$id")
                }
            )
        }

        composable(
            route = Screen.MemoScreen.name,
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
            EventListScreen(eventViewModel = eventViewModel) {
                navController.popBackStack()
            }
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
                weatherViewModel = weatherViewModel,
                homeViewModel = homeViewModel,
                onBack = { navController.popBackStack() },
                onContent = {},
                onWeekStart = {}
            )
        }

        composable(route = Screen.WeatherScreen.name) {
            WeatherScreen(
                weatherViewModel = weatherViewModel,
                onBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.EventScreen.name,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                )
            }
        ) {
            EventScreen(
                eventViewModel = eventViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Screen.ReminderDetailScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            ReminderDetail(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel,
                onBack = { navController.popBackStack() },
                onUpdate = { id ->
                    navController.navigate("${Screen.UpdateReminderScreen.name}/$id")
                }
            )
        }

        composable(
            route = "${Screen.UpdateReminderScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            ReminderUpdate(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel
            ) {
                navController.popBackStack()
            }
        }

        composable(
            route = "${Screen.TaskDetailScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            TaskDetail(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel,
                onBack = { navController.popBackStack() }) { id ->
                navController.navigate("${Screen.UpdateTaskScreen.name}/$id")
            }
        }

        composable(
            route = "${Screen.UpdateTaskScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            TaskUpdate(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel
            ) {
                navController.popBackStack()
            }
        }

        composable(
            route = "${Screen.BirthdayDetailScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            BirthdayDetail(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel,
                onBack = { navController.popBackStack() },
                onUpdate = { id ->
                    navController.navigate("${Screen.UpdateBirthdayScreen.name}/$id")
                }
            )
        }

        composable(
            route = "${Screen.UpdateBirthdayScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            BirthdayUpdate(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel
            ) {
                navController.popBackStack()
            }
        }

        composable(
            route = "${Screen.TravelDetailScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            TravelDetail(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel,
                onBack = { navController.popBackStack() }
            ) { id ->
                navController.navigate("${Screen.UpdateTravelScreen.name}/$id")
            }
        }

        composable(
            route = "${Screen.UpdateTravelScreen.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { navBackStackEntry ->
            TravelUpdate(
                id = navBackStackEntry.arguments?.getInt("id")!!,
                eventViewModel = eventViewModel
            ) {
                navController.popBackStack()
            }
        }
    }
}