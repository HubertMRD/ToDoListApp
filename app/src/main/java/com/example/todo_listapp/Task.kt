package com.example.todo_listapp

import java.util.UUID

data class Task(
    var id: UUID = UUID.randomUUID(),
    var body: String = "",
    var completed: Boolean = false
) {
    companion object {
        fun newId(): UUID = UUID.randomUUID()
    }
}
_________________
package com.example.todo_listapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskCard(
    task: Task,
    toggleCompleted: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable { toggleCompleted(task) }
    ) {
        Checkbox(
            checked = task.completed,
            onCheckedChange = { toggleCompleted(task) }
        )
        Text(
            text = task.body,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
