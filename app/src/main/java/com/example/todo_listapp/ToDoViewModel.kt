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
        if (body.isNotBlank()) {
            taskList.add(Task(body = body))
        }
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
    }

    fun toggleTaskCompleted(task: Task) {
        val index = taskList.indexOf(task)
        taskList[index] = taskList[index].copy(completed = !task.completed)
    }

    fun uncheckAll() {
        for (i in taskList.indices) {
            taskList[i] = taskList[i].copy(completed = false)
        }
    }

    fun deleteCompleted() {
        taskList.removeAll { it.completed }
    }

    fun moveToBottom(task: Task) {
        taskList.remove(task)
        taskList.add(task.copy()) // copy avoids key collision
    }
}
