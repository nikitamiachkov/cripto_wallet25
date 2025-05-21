package com.example.kaban2.Screens.BuyScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.Navigation.NavigationRoutes
import com.example.kaban2.Screens.Components.CategoryItem

import com.example.kaban2.Screens.Components.CategoryItem
import com.example.kaban2.Screens.MainScreen.MainScreenViewModel
import kotlinx.coroutines.runBlocking
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.kaban2.Domain.models.Cripto

import com.example.kaban2.Screens.Components.CardCripto

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import com.example.kaban2.Domain.Constant.supabase


@Composable
fun BuyScreen(navController: NavHostController, mapScreenViewModel: BuyScreenViewModel = viewModel()) {
    Surface(color = Color(0xFF121212), modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Купить крипту",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }

    val textSearch = remember { mutableStateOf("") }
    val kolvo = mapScreenViewModel.kolvo

    // Наблюдаемое состояние для списка категорий из ViewModel


    // Наблюдаемое состояние для списка книг из ViewModel
    val books = mapScreenViewModel.books.observeAsState(emptyList())
    val books2 = listOf<Cripto>()

    // Состояние для хранения выбранной категории, изначально не выбрана (-1)
    val selectedCategory = remember { mutableIntStateOf(-1) }

    val resultState by mapScreenViewModel.resultState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.width(26.dp))
        Text(
            text = "Рынок:",
            fontSize = 25.sp,
            color = Color(0xFFEFECEC),
            fontWeight = FontWeight.Bold
        )



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
                        CardCripto(
                            book = books.value[index],
                            getUrl = { imageName  ->
                                mapScreenViewModel.getUrlImage(books.value[index].image) // ✅ можно передавать suspend
                            },
                            navController = navController )
                    }
                }
            }
        }
    }

}