package com.example.kaban2.Screens.SignInScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaban2.Domain.Constant
import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.Domain.State.SignInState
import com.example.kaban2.Domain.Utils.isEmailValid
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.logging.SupabaseLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Этот код определяет класс SignInViewModel на Kotlin, использующей архитектуру MVVM (Model-View-ViewModel).
 * Он управляет состоянием пользовательского интерфейса и результатом операции входа пользователя
 * */

class SignInViewModel : ViewModel() {
    // Изменяемое состояние, содержащее текущее SignInState. uiState обеспечивает доступ только для чтения к этому состоянию,
    // делая его наблюдаемым для пользовательского интерфейса
    private val _uiState = mutableStateOf(SignInState())
    val uiState: SignInState get() = _uiState.value

    // MutableStateFlow, содержащий текущее ResultState. resultState обеспечивает доступ только для чтения к этому потоку состояний;
    // пользовательский интерфейс может собирать обновления из этого потока
    private val _resultState = MutableStateFlow<ResultState>(ResultState.Initialized)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    // Эта функция обновляет _uiState новым SignInState.
    // Важно отметить, что она также немедленно проверяет действительность адреса электронной почты и сбрасывает resultState в Initialized
    fun updateState(newState: SignInState) {
        _uiState.value = newState
        _uiState.value.errorEmail = _uiState.value.email.isEmailValid()
        _resultState.value = ResultState.Initialized
    }

    // Основная функция, которая обрабатывает процесс входа в систему
    fun signIn() {
        _resultState.value = ResultState.Loading
        if (_uiState.value.errorEmail) {
            viewModelScope.launch {
                try {
                    Constant.supabase.auth.signInWith(Email)
                    {
                        email = _uiState.value.email
                        password = _uiState.value.password
                    }
                    Log.d("SignIn", "Success")

                    _resultState.value = ResultState.Success("Success")
                } catch (_ex: AuthRestException) {
                    Log.d("SignIn", _ex.message.toString())
                    Log.d("SignIn", _ex.errorCode.toString())
                    Log.d("SignIn", _ex.errorDescription.toString())

                    _resultState.value = ResultState.Error(_ex.errorDescription ?: "Ошибка получения данных")
                }
            }
        }
        else{
            _resultState.value = ResultState.Error( "Ошибка ввода почты")
        }
    }


    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            errorEmail = !email.contains("@")
        )
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            errorPassword = password.length < 6
        )
    }

}