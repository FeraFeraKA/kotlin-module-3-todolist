package com.example.todolist.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.R
import com.example.todolist.presentation.ui.screen.TodoDetailScreen
import com.example.todolist.presentation.ui.screen.TodoListScreen
import com.example.todolist.presentation.viewmodel.TodoUiState
import com.example.todolist.presentation.viewmodel.TodoViewModel

private const val LIST = "list"
private const val TODO_ID = "todoId"
private const val DETAIL = "detail/{$TODO_ID}"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoNavigation(model: TodoViewModel) {
    val navigation = rememberNavController()
    val state by model.state.collectAsStateWithLifecycle()

    NavHost(navController = navigation, startDestination = LIST) {
        composable(LIST) {
            Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.todos)) }) }) { insets ->
                TodoListScreen(
                    state = state,
                    onOpen = { navigation.navigate("detail/$it") },
                    onToggle = model::toggle,
                    onRetry = model::refresh,
                    modifier = Modifier.padding(insets),
                )
            }
        }
        composable(
            route = DETAIL,
            arguments = listOf(navArgument(TODO_ID) { type = NavType.IntType }),
        ) { entry ->
            val id = entry.arguments?.getInt(TODO_ID)
            val tasks = (state as? TodoUiState.Content)?.todos
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.details)) },
                        navigationIcon = {
                            TextButton(onClick = { navigation.popBackStack() }) {
                                Text(stringResource(R.string.back))
                            }
                        },
                    )
                },
            ) { insets ->
                if (tasks == null) {
                    TodoListScreen(
                        state = state,
                        onOpen = {},
                        onToggle = {},
                        onRetry = model::refresh,
                        modifier = Modifier.padding(insets),
                    )
                } else {
                    TodoDetailScreen(tasks.firstOrNull { it.id == id }, Modifier.padding(insets))
                }
            }
        }
    }
}
