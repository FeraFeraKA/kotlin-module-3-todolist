package com.example.todolist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoUiTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Before
    fun waitForJson() {
        compose.waitUntil(5_000) {
            compose.onAllNodesWithText("Купить молоко").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun allThreeJsonTasksAreDisplayed() {
        compose.onNodeWithText("Купить молоко").assertIsDisplayed()
        compose.onNodeWithText("Позвонить маме").assertIsDisplayed()
        compose.onNodeWithText("Сделать ДЗ по Android").assertIsDisplayed()
    }

    @Test
    fun checkboxTogglesCompletionInBothDirections() {
        val checkbox = compose.onNodeWithTag("todo-checkbox-1")
        checkbox.assertIsOff().performClick()
        checkbox.assertIsOn()
        compose.onNodeWithText("2 из 3 выполнено").assertIsDisplayed()

        checkbox.performClick().assertIsOff()
        compose.onNodeWithText("1 из 3 выполнено").assertIsDisplayed()
    }

    @Test
    fun listOpensDetailsAndReturnsToList() {
        compose.onNodeWithTag("todo-row-1").performClick()
        compose.onNodeWithTag("todo-detail").assertIsDisplayed()
        compose.onNodeWithText("Купить молоко").assertIsDisplayed()
        compose.onNodeWithText("2 литра, обезжиренное").assertIsDisplayed()
        compose.onNodeWithText("В работе").assertIsDisplayed()

        compose.onNodeWithText("Назад").performClick()
        compose.onNodeWithTag("todo-list").assertIsDisplayed()
        compose.onNodeWithTag("todo-detail").assertDoesNotExist()
    }
}
