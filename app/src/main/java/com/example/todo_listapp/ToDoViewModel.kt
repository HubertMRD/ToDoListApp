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
        if (index != -1) {
            taskList[index] = taskList[index].copy(completed = !task.completed)
        }
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
        val index = taskList.indexOf(task)
        if (index != -1) {
            val updated = taskList[index]
            taskList.removeAt(index)
            taskList.add(updated.copy(id = Task.newId()))
        }
    }
}
________________________
package com.example.todo_listapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel : ViewModel() {

    private var nextId = 0

    val taskList = mutableStateListOf<Task>()

    fun addTask(body: String) {
        if (body.isNotBlank()) {
            taskList.add(Task(id = nextId++, body = body))
        }
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
    }

    fun toggleTaskCompleted(task: Task) {
        val index = taskList.indexOf(task)
        if (index != -1) {
            taskList[index] = task.copy(completed = !task.completed)
        }
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
        taskList.add(task)
    }

    fun populateTaskList() {
        taskList.clear()
        nextId = 0
        repeat(5) {
            addTask("Sample Task ${it + 1}")
        }
    }
}
