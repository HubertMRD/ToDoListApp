package com.example.todo_listapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel: ViewModel() {

    val taskList = mutableStateListOf<Task>()

    fun addTask(body: String){
        taskList.add(Task(body = body))
    }

    fun deleteTask(task: Task){
        taskList.remove(task)
    }

    fun toggleTaskCompleted(task: Task){
        val index = taskList.indexOf(task)
        taskList[index] = taskList[index].copy(completed = !task.completed)
    }

}