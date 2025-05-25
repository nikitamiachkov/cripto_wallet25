package com.example.kaban2.Screens.SignInScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.R
import com.example.kaban2.Screens.Components.ButtonNavigation
import com.example.kaban2.Screens.Components.TextFieldPassword
import com.example.kaban2.Screens.Components.TextFieldEmail
import com.example.kaban2.Navigation.NavigationRoutes


/**
 * composable-функция SignInScreen для экрана входа
 * */
@Composable
// navController: NavHostController:  Объект для навигации между экранами.
// signInViewModel: SignInViewModel = viewModel() :  ViewModel, управляющий состоянием и логикой экрана входа.
fun SignInScreen(navController: NavHostController, signInViewModel: SignInViewModel = viewModel()) {

    val resultState by signInViewModel.resultState.collectAsState() // использует collectAsState() для преобразования потока состояний (Flow<ResultState>) из ViewModel в состояние
    val uiState = signInViewModel.uiState //получает текущее состояние UI из ViewModel

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color.Black)
    ) {
        //Вызовы TextFieldEmail и TextFieldPassword создают поля ввода для email и пароля, соответственно, используя ранее определенные функции
        TextFieldEmail(value = uiState.email, error = uiState.errorEmail,
            onvaluechange = { it -> signInViewModel.updateState(uiState.copy(email = it)) })
        Spacer(Modifier.height(10.dp))
        TextFieldPassword(value = uiState.password, onvaluechange = { it -> signInViewModel.updateState(uiState.copy(password = it)) }, "пароль" )
        Spacer(Modifier.height(10.dp))

        //Этот when-выражение обрабатывает различные состояния (ResultState) операции входа
        when (resultState) {
            is ResultState.Error -> {
                ButtonNavigation(stringResource(R.string.name)) {
                    signInViewModel.signIn().toString()

                }
                Text((resultState as ResultState.Error).message)
            }

            is ResultState.Initialized -> {
                ButtonNavigation(stringResource(R.string.name)) {
                    signInViewModel.signIn().toString()
                }
            }

            ResultState.Loading -> {
                CircularProgressIndicator()
            }

            is ResultState.Success -> {
                navController.navigate(NavigationRoutes.MAIN)
                {
                    popUpTo(NavigationRoutes.SIGNIN) {
                        inclusive = true
                    }
                }
            }
        }

        Text(
            "Создать аккаунт",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W600,
            modifier = Modifier.clickable {
                navController.navigate(NavigationRoutes.SIGNUP)
            }
        )
    }
}

@Preview(locale = "es")
@Composable
fun PreviewSigIn() {
    SignInScreen(rememberNavController())
}