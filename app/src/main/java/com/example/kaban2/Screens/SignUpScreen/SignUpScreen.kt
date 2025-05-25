package com.example.kaban2.Screens.SignUpScreen

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.Navigation.NavigationRoutes
import com.example.kaban2.Screens.Components.ButtonNavigation
import com.example.kaban2.Screens.Components.TextFieldEmail
import com.example.kaban2.Screens.Components.TextFieldPassword
import com.example.kaban2.Screens.Components.TextFieldStandart
import java.util.Calendar
import java.util.Date

/**
 * composable-функция SignUpScreen для экрана регистрации пользователя
 * */

@Composable
// navController: NavHostController:  Объект для навигации между экранами.
// sigUpViewModel: SignUpViewModel = viewModel() :  ViewModel, управляющий состоянием и логикой экрана входа.
fun SignUpScreen(navController: NavHostController, sigUpViewModel: SignUpViewModel = viewModel()) {

    val uiState = sigUpViewModel.uiState // Получение текущего состояния UI из ViewMode

    val mContext = LocalContext.current // Получение контекста Android

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("Выберите дату") }

    val resultState by sigUpViewModel.resultState.collectAsState() //Собирает значения из StateFlow в ViewModel, используя collectAsState() для синхронного доступа в Composable

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color.Black)
    ) {

        TextFieldEmail(uiState.email,uiState.isEmailError) { sigUpViewModel.updateState(uiState.copy(email = it)) }
        Spacer(Modifier.height(10.dp))

        TextFieldStandart(value = uiState.username, onvaluechange = { it -> sigUpViewModel.updateState(uiState.copy(username = it)) }, "имя")
        Spacer(Modifier.height(10.dp))

        TextFieldStandart(
            value = uiState.surname,
            onvaluechange = { it -> sigUpViewModel.updateState(uiState.copy(surname = it)) },
            "фамилия"
        )
        Spacer(Modifier.height(10.dp))

        TextFieldPassword(
            value = uiState.password,
            onvaluechange = { it -> sigUpViewModel.updateState(uiState.copy(password = it)) },"пароль")
        Spacer(Modifier.height(10.dp))

        TextFieldPassword(
            value = uiState.confirmPassword,
            onvaluechange = { it -> sigUpViewModel.updateState(uiState.copy(confirmPassword = it)) }, "подтвердите пароль")
        Spacer(Modifier.height(10.dp))

        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mYear-${mMonth+1}-$mDayOfMonth"
                sigUpViewModel.updateState(uiState.copy(dateBirth = "$mYear-${mMonth+1}-$mDayOfMonth"))
            }, mYear, mMonth, mDay
        )

        Text(
            text = mDate.value,
            color = Color.White,
            modifier = Modifier.clickable {
                mDatePickerDialog.show()
            }
        )
        Spacer(Modifier.height(10.dp))
        when (resultState) {
            is ResultState.Error -> {
                ButtonNavigation("Зарегистрироваться") { sigUpViewModel.signUp() }
                Text((resultState as ResultState.Error).message)
            }
            is ResultState.Initialized -> {
                ButtonNavigation("Зарегистрироваться") { sigUpViewModel.signUp() }
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
            "Уже есть аккаунт",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W600,
            modifier = Modifier.clickable {
                navController.navigate(NavigationRoutes.SIGNIN)
            }
        )

    }
}