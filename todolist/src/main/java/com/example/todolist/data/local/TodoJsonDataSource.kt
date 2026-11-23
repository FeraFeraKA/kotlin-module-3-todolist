package com.example.todolist.data.local

import com.example.todolist.data.model.TodoItemDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoJsonDataSource(private val openJson: () -> InputStream) {
    private val gson = Gson()
    private val todoListType = object : TypeToken<List<TodoItemDto>>() {}.type

    suspend fun getTodos(): List<TodoItemDto> = withContext(Dispatchers.IO) {
        openJson().bufferedReader(Charsets.UTF_8).use { reader ->
            gson.fromJson<List<TodoItemDto>>(reader, todoListType)
                ?: error("Expected an array of tasks in todos.json")
        }
    }
}
