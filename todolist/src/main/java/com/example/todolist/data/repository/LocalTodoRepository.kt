package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LocalTodoRepository(private val dataSource: TodoJsonDataSource) : TodoRepository {
    private val mutex = Mutex()
    private var cachedTodos: List<TodoItem>? = null

    override suspend fun getTodos(): List<TodoItem> = mutex.withLock {
        loadTodos().toList()
    }

    override suspend fun toggleTodo(id: Int) = mutex.withLock {
        cachedTodos = loadTodos().map { todo ->
            if (todo.id == id) todo.copy(isCompleted = !todo.isCompleted) else todo
        }
    }

    private suspend fun loadTodos(): List<TodoItem> =
        cachedTodos ?: dataSource.getTodos().map { it.toDomain() }.also { cachedTodos = it }
}
