package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.LocalTodoRepository
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import com.example.todolist.navigation.TodoNavigation
import com.example.todolist.presentation.ui.theme.TodoTheme
import com.example.todolist.presentation.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    private val model by viewModels<TodoViewModel> {
        viewModelFactory {
            initializer {
                val assets = application.assets
                val source = TodoJsonDataSource { assets.open("todos.json") }
                val repository = LocalTodoRepository(source)
                TodoViewModel(GetTodosUseCase(repository), ToggleTodoUseCase(repository))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                TodoNavigation(model)
            }
        }
    }
}
