package com.example.kaban2.Screens.Components

import android.net.Uri
import android.util.Log
import android.widget.Toast
//import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kaban2.Domain.models.Cripto
import com.example.kaban2.R

import coil.size.Size
import com.example.kaban2.Screens.MainScreen.MainScreenViewModel


@Composable
fun CardCripto(book: Cripto,
               getUrl: suspend  (String) -> String,
               navController: NavController) {

    val viewModel: CardCriptoViewModel = viewModel()
    val viewModel1: MainScreenViewModel = viewModel()

    val context = LocalContext.current

    // Слушаем сообщения из ViewModel и показываем Toast
    LaunchedEffect(Unit) {
        viewModel.userMessage.collect { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.clearMessage()
            }
        }
    }


    // Состояние для хранения URL изображения фона
    var imageUrl by remember { mutableStateOf("") }

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
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Создание состояния для загрузки изображения с использованием библиотеки Coil
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                    .size(Size.ORIGINAL).build() // Запрос изображения с оригинальным размером
            ).state

            // Запускаем эффект, который обновляет imageUrl при изменении book
            LaunchedEffect(book) {
                imageUrl = getUrl(book.image)
            }
            // Проверяем состояние загрузки изображения
            if (imageState is AsyncImagePainter.State.Loading) {
                // Если изображение загружается, показываем индикатор загрузки
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                    Log.d("buck", imageUrl)
                }
            }
            // Если произошла ошибка при загрузке изображения, то ставим заглушку
            if (imageState is AsyncImagePainter.State.Error) {
                Image(

                    painter = painterResource(R.drawable.icon),
                    contentDescription = book.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),

                    contentScale = ContentScale.Crop
                )
            }
            // Если изображение успешно загружено, используем загруженное изображение
            if (imageState is AsyncImagePainter.State.Success) {
                Image(

                    painter = imageState.painter,
                    contentDescription = book.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = book.name.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Курс: ${book.cost}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Вчерашний курс: ${book.last_cost}",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    // Логика покупки
                    viewModel.buyCripto(book)
                    viewModel.triggerReload()
                    viewModel1.triggerReload()
                    Log.d("CardCripto", "Купить: ${book.name}")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Купить")
            }

            Button(
                onClick = {
                    // Логика продажи
                    viewModel.sellCripto(book)
                    viewModel1.triggerReload()
                    Log.d("CardCripto", "Продать: ${book.name}")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Продать")
            }


        }
    }
}