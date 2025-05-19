package com.example.kaban2.Screens.MainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaban2.Screens.BuyScreen.BuyScreen
import com.example.kaban2.Screens.Components.BottomNavItem
import com.example.kaban2.Screens.Components.UserProfileHeader
import com.example.kaban2.Screens.DarkBottomNavigationBar
import com.example.kaban2.Screens.RateScreen.RateScreen


@Composable
fun MainScreen(navController: NavHostController) {
    val innerNavController = rememberNavController()
    val viewModel: MainScreenViewModel = viewModel()

    val username = viewModel.username
    val balance = viewModel.balance

    Scaffold(
        containerColor = Color(0xFF121212), // Тёмный фон всего экрана
        bottomBar = {
            DarkBottomNavigationBar(innerNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = innerNavController,
            startDestination = BottomNavItem.Main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Main.route) {
                Surface(
                    color = Color(0xFF121212),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Заголовок
                        Text(
                            text = "Главная страница",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Аватар + имя + баланс
                        UserProfileHeader(username = username, balance = balance)
                    }
                }
            }
            composable(BottomNavItem.Buy.route) {
                BuyScreen(innerNavController)
            }
            composable(BottomNavItem.Rate.route) {
                RateScreen(innerNavController)
            }
        }
    }
}