
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.filters.SmallTest
import com.example.kaban2.Domain.models.Cripto
import com.example.kaban2.Domain.models.User_cripto
import com.example.kaban2.Screens.Components.CardCriptoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.annotation.Config
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class CardCriptoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CardCriptoViewModel

    @Before
    fun setup() {
        viewModel = CardCriptoViewModel()
    }

    @Test
    fun retfre() = runTest(UnconfinedTestDispatcher()) {
        // Подготовка исходных данных
        val testCrypto = Cripto(id = 1, name ="dewde", cost = 100.0, image = "efefef", last_cost = 123.0)
        val initialCryptos = mutableListOf(User_cripto(1, cripto = 1, quantity = 0, purchase_price = 1.1, user_id="erfrefer"))
        viewModel._criptoList.value = initialCryptos

        // Выполнение операции покупки
        viewModel.buyCripto(testCrypto)
        advanceUntilIdle()

        // Ожидания
        assertThat(viewModel.criptoList.value.size, `is`(1))
        assertThat(viewModel.criptoList.value.first().quantity, `is`(1))
    }

    @Test
    fun `sellCripto should decrease quantity of a specific crypto`() = runTest(UnconfinedTestDispatcher()) {
        // Исходные данные
        val testCrypto = Cripto(id = 1, name ="dewde", cost = 100.0, image = "efefef", last_cost = 123.0)
        val initialCryptos = mutableListOf(User_cripto(1, cripto = 1, quantity = 0, purchase_price = 1.1, user_id="erfrefer"))
        viewModel._criptoList.value = initialCryptos

        // Продаем криптовалюту
        viewModel.sellCripto(testCrypto)
        advanceUntilIdle()

        // Ожидаемое поведение
        assertThat(viewModel.criptoList.value.size, `is`(1))
        assertThat(viewModel.criptoList.value.first().quantity, `is`(1))
    }

    @Test
    fun `sellCripto should remove item completely if last unit sold`() = runTest(UnconfinedTestDispatcher()) {
        // Исходные данные
        val testCrypto = Cripto(id = 1, name ="dewde", cost = 100.0, image = "efefef", last_cost = 123.0)
        val initialCryptos = mutableListOf(User_cripto(1, cripto = 1, quantity = 0, purchase_price = 1.1, user_id="erfrefer"))
        viewModel._criptoList.value = initialCryptos

        // Продаем последнюю единицу
        viewModel.sellCripto(testCrypto)
        advanceUntilIdle()

        // Ожидаемый результат
        assertThat(viewModel.criptoList.value.isEmpty(), `is`(true))
    }

    @Test
    fun `buyCripto should notify about insufficient funds if balance is low`() = runTest(UnconfinedTestDispatcher()) {
        // Исходные данные
        val testCrypto = Cripto(id = 1, name ="dewde", cost = 100.0, image = "efefef", last_cost = 123.0)
        val initialBalance = 50.0f
        viewModel.showMessage(initialBalance.toString())

        // Покупка крипты
        viewModel.buyCripto(testCrypto)
        advanceUntilIdle()

        // Ожидаемое сообщение
        assertThat(viewModel.userMessage.value, `is`("У вас недостаточно средств"))
    }

    @Test
    fun `sellCripto should inform that there's no such cryptocurrency`() = runTest(UnconfinedTestDispatcher()) {
        // Исходные данные
        val testCrypto = Cripto(id = 1, name ="dewde", cost = 100.0, image = "efefef", last_cost = 123.0)
        val initialCryptos = mutableListOf<User_cripto>()

        viewModel._criptoList.value = initialCryptos

        // Продажа несуществующей валюты
        viewModel.sellCripto(testCrypto)
        advanceUntilIdle()

        // Ожидаемое сообщение
        assertThat(viewModel.userMessage.value, `is`("У вас нет такой криптовалюты"))
    }
}