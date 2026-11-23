package com.example.todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface TodoUiState {
    data object Loading : TodoUiState
    data class Content(val todos: List<TodoItem>) : TodoUiState
    data object Error : TodoUiState
}

class TodoViewModel(
    private val getTodos: GetTodosUseCase,
    private val toggleTodo: ToggleTodoUseCase,
) : ViewModel() {
    private val stateMutex = Mutex()
    private val _state = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val state = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        update { _state.value = TodoUiState.Loading }
    }

    fun toggle(id: Int) {
        update { toggleTodo(id) }
    }

    private fun update(beforeRead: suspend () -> Unit) {
        viewModelScope.launch {
            stateMutex.withLock {
                try {
                    beforeRead()
                    _state.value = TodoUiState.Content(getTodos())
                } catch (cancelled: CancellationException) {
                    throw cancelled
                } catch (_: Exception) {
                    _state.value = TodoUiState.Error
                }
            }
        }
    }
}
