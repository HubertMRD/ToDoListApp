package com.example.todo_listapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel : ViewModel() {

    val taskList = mutableStateListOf<Task>()

    fun populateTaskList() {
        addTask("Buy eggs")
        addTask("Buy milk")
        addTask("Buy sugar")
        addTask("Buy flour")
    }

    fun addTask(body: String) {
        taskList.add(Task(body = body))
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
    }

    fun toggleTaskCompleted(task: Task) {
        val index = taskList.indexOf(task)
        taskList[index] = taskList[index].copy(completed = !task.completed)
    }

    // NEW: Uncheck all tasks
    fun uncheckAll() {
        for (i in taskList.indices) {
            taskList[i] = taskList[i].copy(completed = false)
        }
    }

    // NEW: Delete all completed tasks
    fun deleteCompleted() {
        taskList.removeAll { it.completed }
    }

    // NEW: Move task to bottom
    fun moveToBottom(task: Task) {
        taskList.remove(task)
        taskList.add(task.copy()) // copy to avoid key collision
    }
}
