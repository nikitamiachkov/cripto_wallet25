package com.example.kaban2

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

// enum ResultState
sealed class ResultState {
    object Initialized : ResultState()
    object Loading : ResultState()
    object Success : ResultState()
    class Error(val message: String) : ResultState()
}

// ViewModel для теста
class FakeBuyScreenViewModel {
    var balance = 1000.0
    val resultState = MutableStateFlow<ResultState>(ResultState.Initialized)

    fun buyCoin(amount: Double) {
        if (balance >= amount) {
            balance -= amount
            resultState.value = ResultState.Success
        } else {
            resultState.value = ResultState.Error("Недостаточно средств")
        }
    }
}

// Юнит тест/проверка покупки криптовалюты
class BuyUnitTest {

    @Test
    fun Покупка_криптовалюты_уменьшает_баланс_и_возвращает_Success() = runTest {
        val viewModel = FakeBuyScreenViewModel()
        val initialBalance = viewModel.balance

        val purchaseAmount = 200.0
        viewModel.buyCoin(purchaseAmount)

        // Проверка уменьшения баланса
        assertEquals(initialBalance - purchaseAmount, viewModel.balance, 0.01)

        // Проверка, что операция завершилась успехом
        assertTrue(viewModel.resultState.value is ResultState.Success)
    }
}