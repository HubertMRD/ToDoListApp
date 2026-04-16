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
