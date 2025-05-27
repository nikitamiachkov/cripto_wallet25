package com.example.kaban2

import com.example.kaban2.Screens.SignInScreen.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class SignInViewModelTest {

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        viewModel = SignInViewModel()
    }

    @Test
    fun updateEmail_withValidEmail_setsErrorEmailFalse() = runTest {
        viewModel.updateEmail("test15@example.com")
        val state = viewModel.uiState
        assertEquals("test15@example.com", state.email)
        assertFalse(state.errorEmail)
    }

    @Test
    fun updateEmail_withInvalidEmail_setsErrorEmailTrue() = runTest {
        viewModel.updateEmail("invalidemail")
        val state = viewModel.uiState
        assertEquals("invalidemail", state.email)
        assertTrue(state.errorEmail)
    }

    @Test
    fun updatePassword_withShortPassword_setsErrorPasswordTrue() = runTest {
        viewModel.updatePassword("123")
        val state = viewModel.uiState
        assertEquals("123", state.password)
        assertTrue(state.errorPassword)
    }

    @Test
    fun updatePassword_withValidPassword_setsErrorPasswordFalse() = runTest {
        viewModel.updatePassword("123456")
        val state = viewModel.uiState
        assertEquals("123456", state.password)
        assertFalse(state.errorPassword)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}