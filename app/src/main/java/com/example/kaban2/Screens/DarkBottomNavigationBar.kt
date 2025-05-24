package com.example.kaban2.Screens

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kaban2.Screens.Components.BottomNavItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color


@Composable
fun DarkBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Main,
        BottomNavItem.Buy,
        BottomNavItem.Rate
    )

    NavigationBar(
        containerColor = Color.DarkGray, // фон навигации
        contentColor = Color.White       // цвет текста и иконок
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { item.icon() },
                label = { Text(item.label, color = Color.White) },
                selected = currentRoute == item.route,
                onClick = {
                    val currentRoute = navController.currentDestination?.route
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        // Если пользователь нажал на уже выбранный пункт (например, Main), принудительно перезапускаем его
                        navController.navigate(item.route) {
                            popUpTo(item.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    indicatorColor = Color.Gray
                )
            )
        }
    }
}
