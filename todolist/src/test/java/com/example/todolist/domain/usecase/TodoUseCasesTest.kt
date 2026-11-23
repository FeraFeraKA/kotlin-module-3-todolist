package com.example.todolist.domain.usecase

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.LocalTodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TodoUseCasesTest {
    private val repository = LocalTodoRepository(TodoJsonDataSource {
        checkNotNull(javaClass.getResourceAsStream("/todos.json"))
    })
    private val getTodos = GetTodosUseCase(repository)
    private val toggleTodo = ToggleTodoUseCase(repository)

    @Test
    fun getTodosReturnsThreeTasksFromJson() = runTest {
        val tasks = getTodos()
        assertEquals(3, tasks.size)
        assertEquals(listOf(1, 2, 3), tasks.map { it.id })
        assertEquals(listOf(false, true, false), tasks.map { it.isCompleted })
    }

    @Test
    fun toggleTodoChangesCompletionAndLeavesOtherTasksUnchanged() = runTest {
        val before = getTodos()
        assertFalse(before.first().isCompleted)

        toggleTodo(1)
        val after = getTodos()
        assertTrue(after.first().isCompleted)
        assertEquals(before.drop(1), after.drop(1))

        toggleTodo(1)
        assertEquals(before, getTodos())
    }
}
