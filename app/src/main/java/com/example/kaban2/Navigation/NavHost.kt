package com.example.kaban2.Navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaban2.Screens.BuyScreen.BuyScreen
import com.example.kaban2.Screens.DarkBottomNavigationBar
import com.example.kaban2.Screens.MainScreen.MainScreen
import com.example.kaban2.Screens.RateScreen.RateScreen
import com.example.kaban2.Screens.SignInScreen.SignInScreen
import com.example.kaban2.Screens.SignUpScreen.SignUpScreen
import com.example.kaban2.Screens.SplashScreen.SplashScreen


/**
 *Реализация навигации в приложении
 * */


@Composable
fun NavHost() {
    val navController = rememberNavController() //это функция Compose, которая запоминает экземпляр NavController между перекомпозициями, обеспечивая сохранение состояния навигации

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH) {
                //Это функция, которая определяет composable-маршрут внутри NavHost. Каждый composable  соответствует определенному экрану приложения

                composable(NavigationRoutes.SPLASH)
                {
                    SplashScreen(navController)
                }
                composable(NavigationRoutes.SIGNIN)
                {
                    SignInScreen(navController)
                }
                composable(NavigationRoutes.SIGNUP)
                {
                    SignUpScreen(navController)
                }
                composable(NavigationRoutes.MAIN)
                {
                    MainScreen(navController)
                }
                composable(NavigationRoutes.RATE)
                {
                    RateScreen(navController)
                }
                composable(NavigationRoutes.BUY)
                {
                    BuyScreen(navController)
                }
            }
        }
    }
}