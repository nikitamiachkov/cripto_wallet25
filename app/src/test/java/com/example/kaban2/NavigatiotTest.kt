package com.example.kaban2
import android.os.Build
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.kaban2.Screens.MainScreen.MainScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mockStatic

@RunWith(JUnit4::class)
class MainScreenNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()  // Используем createComposeRule вместо Android-версии

    private lateinit var navController: NavHostController

    @Before
    fun setup() {

            composeTestRule.setContent {
            navController = rememberNavController()
            MainScreen(navController = navController)
        }
    }

    @Test
    fun mainScreen_initialState_showsMainScreen() {
        composeTestRule.onNodeWithText("Main Screen").assertIsDisplayed()
    }

    @Test
    fun navigationToBuyScreen_works() {
        composeTestRule.onNodeWithContentDescription("Buy").performClick()
        composeTestRule.onNodeWithContentDescription("Buy Screen").assertIsDisplayed()
    }

    @Test
    fun navigationToRateScreen_works() {
        composeTestRule.onNodeWithContentDescription("Rate").performClick()
        composeTestRule.onNodeWithContentDescription("Rate Screen").assertIsDisplayed()
    }
}