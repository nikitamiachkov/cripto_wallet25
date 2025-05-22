package com.example.kaban2.Screens.MainScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.kaban2.Screens.Components.CardCripto
import com.example.kaban2.Screens.Components.CardCriptoForMain
import com.example.kaban2.Screens.Components.UserProfileHeader
import com.example.kaban2.Screens.DarkBottomNavigationBar
import com.example.kaban2.Screens.RateScreen.RateScreen
import kotlinx.coroutines.async
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.LiveData
import com.example.kaban2.Domain.models.Cripto
import com.example.kaban2.Domain.models.User_cripto
import com.example.kaban2.Screens.Components.CardCriptoViewModel


@Composable
fun MainScreen(navController: NavHostController) {
    val innerNavController = rememberNavController()
    val viewModel: MainScreenViewModel = viewModel()

    val viewModel2: CardCriptoViewModel = viewModel()

    val criptoList by viewModel2.criptoList.collectAsState()

    val username = viewModel.username
    val balance = viewModel.balance

    var cripto = viewModel.cripto
    //cripto = criptoList
    val kolvo = viewModel.kolvo

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

                        Spacer(modifier = Modifier.height(68.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.DarkGray) // Задали тёмный фон
                                .padding(top = 10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Ваши активы",
                                color = Color.White,           // Белый цвет текста
                                fontWeight = FontWeight.Bold, // Жирный шрифт
                                style = MaterialTheme.typography.headlineSmall // Размер шрифта заголовка H5
                            )

                            Spacer(modifier = Modifier.height(8.dp)) // Небольшой отступ между текстом и списком

                            LazyColumn {
                                Log.d("buck", kolvo.toString())
                                items(kolvo) { index ->
                                    var currentCrypto by remember { mutableStateOf<Cripto?>(null) }

                                    LaunchedEffect(key1 = Unit) {
                                        currentCrypto = async {
                                            viewModel.getCripto(cripto.value?.get(index)?.cripto ?: index)
                                        }.await()
                                    }

                                    currentCrypto?.let { cryptoItem ->
                                        CardCriptoForMain(
                                            book = cryptoItem,
                                            quantity = cripto.value?.get(index)?.quantity ?: 0,
                                            getUrl = { imageName  ->
                                                viewModel.getUrlImage(cryptoItem.image) // ✅ можно передавать suspend
                                            },
                                            navController = navController
                                        )
                                    }
                                }

                            }
                        }





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