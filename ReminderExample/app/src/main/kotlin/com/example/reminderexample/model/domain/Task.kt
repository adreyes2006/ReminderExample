package com.example.reminderexample.model.domain

import com.example.reminderexample.model.dtos.TaskDto

data class Task(
    val id: Int,
    val title: String,
    val description: String,
)

fun Task.toTaskDto() =
    TaskDto(
        id = this.id,
        description = this.description,
        title = this.title,
        dueDate = "",
    )
