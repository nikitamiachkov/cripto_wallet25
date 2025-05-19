package com.example.kaban2.Screens.SignUpScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.kaban2.Domain.State.SignUpState
import com.example.kaban2.Domain.Utils.isEmailValid
import com.example.kaban2.Domain.models.Profile
import com.example.kaban2.Domain.models.Resources
import com.example.kaban2.Navigation.NavigationRoutes
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import com.example.kaban2.Domain.Constant.supabase
import com.example.kaban2.Domain.State.ResultState
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.from
import kotlinx.atomicfu.TraceBase.None
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * Этот код определяет класс SignInViewModel на Kotlin, использующей архитектуру MVVM (Model-View-ViewModel).
 * Он управляет состоянием пользовательского интерфейса и результатом операции входа пользователя
 * */

class SignUpViewModel:ViewModel() {
    // Изменяемое состояние, содержащее текущее SignInState. uiState обеспечивает доступ только для чтения к этому состоянию,
    // делая его наблюдаемым для пользовательского интерфейса
    private val _uiState = mutableStateOf(SignUpState())
    val uiState: SignUpState get() = _uiState.value

    // MutableStateFlow, содержащий текущее ResultState. resultState обеспечивает доступ только для чтения к этому потоку состояний;
    // пользовательский интерфейс может собирать обновления из этого потока
    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    // Эта функция обновляет _uiState новым SignInState.
    // Важно отметить, что она также немедленно проверяет действительность адреса электронной почты и сбрасывает resultState в Initialized
    fun updateState(newState: SignUpState) {
        _uiState.value = newState
        _uiState.value.isEmailError = _uiState.value.email.isEmailValid()
        _resultState.value = ResultState.Initialized
    }

    // Основная функция, которая обрабатывает процесс регистрации в системе пользователя
    fun signUp()
    {
        _resultState.value = ResultState.Loading
        if (_uiState.value.isEmailError && _uiState.value.password== _uiState.value.confirmPassword) {
            viewModelScope.launch {
                try {
                    supabase.auth.signUpWith(Email)
                    {
                        email = _uiState.value.email
                        password = _uiState.value.password
                    }
                    Log.d("SignUp", "Success")

                    val id = supabase.auth.currentUserOrNull()?.id

                    val user = Profile(
                        user_id = id,
                        _uiState.value.username,
                        _uiState.value.surname,
                        _uiState.value.dateBirth)

                    supabase.from("profile").insert(user)
                    //Log.d("SignUp", "Success2")

                    val res = Resources(
                        user_id = id,
                        0.0)


                    supabase.from("resources").insert(res)
                    //Log.d("SignUp", "Success3")

                    _resultState.value = ResultState.Success("Success")


                } catch (_ex: AuthRestException) {
                    Log.d("signUp", _ex.message.toString())
                    Log.d("signUp", _ex.errorCode.toString())
                    Log.d("signUp", _ex.errorDescription.toString())

                    _resultState.value = ResultState.Error(_ex.errorDescription ?: "Ошибка получения данных")
                }
            }
        }else{
            _resultState.value = ResultState.Error( "Ошибка ввода почты")
        }
    }
}