package com.example.todolist.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.todolist.R
import com.example.todolist.domain.model.TodoItem

@Composable
fun TodoRow(todo: TodoItem, onOpen: () -> Unit, onToggle: () -> Unit) {
    val status = stringResource(if (todo.isCompleted) R.string.completed else R.string.in_progress)
    val checkboxLabel = stringResource(R.string.completion_label, todo.title)
    Card(
        onClick = onOpen,
        modifier = Modifier.fillMaxWidth().testTag("todo-row-${todo.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 7.dp, end = 8.dp, bottom = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(todo.title, style = MaterialTheme.typography.titleMedium)
                Text(status, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = { onToggle() },
                modifier = Modifier.testTag("todo-checkbox-${todo.id}")
                    .semantics { contentDescription = checkboxLabel },
            )
        }
    }
}
