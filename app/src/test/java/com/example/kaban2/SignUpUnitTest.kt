package com.example.kaban2

import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.Domain.State.SignUpState
import com.example.kaban2.Screens.SignUpScreen.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel()
    }

    @Test
    fun updateState_updatesEmailCorrectly() {
        val newState = SignUpState(email = "test16@example.com")
        viewModel.updateState(newState)

        assertEquals("test16@example.com", viewModel.uiState.email)
    }

    @Test
    fun signUp_emitsErrorIfEmailIsInvalid() = runTest {
        val invalidEmailState = SignUpState(email = "invalid-email", password = "123456", confirmPassword = "123456")
        viewModel.updateState(invalidEmailState)
        viewModel.signUp()

        assert(viewModel.resultState.value is ResultState.Error)
    }

    @Test
    fun signUp_emitsErrorIfPasswordsDoNotMatch() = runTest {
        val state = SignUpState(email = "valid@email.com", password = "123456", confirmPassword = "wrong")
        viewModel.updateState(state)
        viewModel.signUp()

        assert(viewModel.resultState.value is ResultState.Error)
    }

    /*@Test
    fun signUp_emitsLoadingIfDataIsValid() = runTest {
        val state = SignUpState(email = "valid@email.com", password = "123456", confirmPassword = "123456")
        viewModel.updateState(state)
        viewModel.signUp()

        assertEquals(ResultState.Loading, viewModel.resultState.value)
    }*/

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }
}