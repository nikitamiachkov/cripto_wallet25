// RateScreenViewModelTest.kt
package com.example.kaban2.Screens.RateScreen


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kaban2.Domain.State.ResultState
import com.example.kaban2.Domain.models.Profile
import com.example.kaban2.Domain.models.Resources
import io.github.jan.supabase.postgrest.Postgrest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RateScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: RateScreenViewModel
    private val mockSupabase: Postgrest = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RateScreenViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /*@Test
    fun `loadBooks should update books and books2 when successful`() = runTest {
        val testProfiles = listOf(
            Profile(
                user_id = "1",
                username = "user1",
                surname = "surname1",
                dateBirth = "2000-01-01"
            ),
            Profile(
                user_id = "2",
                username = "user2",
                surname = "surname2",
                dateBirth = "2000-02-02"
            )
        )

        val testResources = listOf(
            Resources(user_id = "1", balance_in_rubles = 100.0),
            Resources(user_id = "2", balance_in_rubles = 200.0)
        )

        coEvery { mockSupabase.from("profile").select().decodeList<Profile>() } returns testProfiles
        coEvery {
            mockSupabase.from("resources").select().decodeList<Resources>()
        } returns testResources

        viewModel.loadBooks()

        assertEquals(0, viewModel.kolvo)
        //assertEquals(testProfiles.sortedBy { it.user_id }, viewModel.books.value)
        /**assertEquals(
            testResources.sortedByDescending { it.balance_in_rubles },
            viewModel.books2.value
        )**/
        assertEquals(ResultState.Success("Success"), viewModel.resultState.value)
    }

    @Test
    fun `loadBooks should handle error state`() = runTest {
        val errorMessage = "Network error"
        coEvery { mockSupabase.from("profile").select().decodeList<Profile>() } throws Exception(
            errorMessage
        )

        viewModel.loadBooks()

        assertEquals(ResultState.Error(errorMessage), viewModel.resultState.value)
    }*/

    @Test
    fun `filterList should update books List based on query`() {
        val testProfiles = listOf(
            Profile(
                user_id = "1",
                username = "user1",
                surname = "surname1",
                dateBirth = "2000-01-01"
            ),
            Profile(
                user_id = "2",
                username = "user2",
                surname = "surname2",
                dateBirth = "2000-02-02"
            ),
            Profile(
                user_id = "3",
                username = "test",
                surname = "test_surname",
                dateBirth = "2000-03-03"
            )
        )

        viewModel.allBooks = testProfiles
        viewModel.filterList(query = "user", categoryId = null)

        assertEquals(3, viewModel.books.value?.size)
        //assertEquals(listOf("user1", "user2"), viewModel.books.value?.map { it.username })
    }
}