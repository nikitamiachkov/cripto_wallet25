package com.example.kaban2.Screens.RateScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.Screens.BuyScreen.BuyScreen
import com.example.kaban2.Screens.BuyScreen.BuyScreenViewModel

import com.example.kaban2.Screens.Components.BottomNavItem
import com.example.kaban2.Screens.Components.CardCripto
import com.example.kaban2.Screens.Components.CardRate
import com.example.kaban2.Screens.Components.UserProfileHeader
import com.example.kaban2.Screens.DarkBottomNavigationBar
import com.example.kaban2.Screens.MainScreen.MainScreenViewModel
import kotlin.collections.get


@Composable
fun RateScreen(navController: NavHostController) {

    val innerNavController = rememberNavController()
    val viewModel: RateScreenViewModel = viewModel()

    val books = viewModel.books.observeAsState(emptyList())
    val books2 = viewModel.books2.observeAsState(emptyList())

    val resultState by viewModel.resultState.collectAsState()
    val kolvo = viewModel.kolvo

    Surface(color = Color(0xFF121212), modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Топ холдеров",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }

    //val username = viewModel.username
    //val balance = viewModel.balance

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.width(26.dp))
        Text(
            text = "Кабаны:",
            fontSize = 25.sp,
            color = Color(0xFFEFECEC),
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            /*.clickable {
                // при клике переходим на экран с фоном
                navController.navigate("main/${Uri.encode(imageUrl)}")
            }*/
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ник:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Баланс:",
                    fontWeight = FontWeight.Bold
                )
            }

        }



        when (resultState) {
            is ResultState.Error ->
                Text(text = (resultState as ResultState.Error).message)

            ResultState.Initialized -> TODO()
            ResultState.Loading -> {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResultState.Success -> {

                    LazyColumn {
                        items(kolvo) { index ->
                            CardRate(
                                username = books.value[index].username,
                                balance =  books2.value[index].balance_in_rubles)

                    }
                }
            }
        }
    }
}