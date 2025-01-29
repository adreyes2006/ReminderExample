package com.example.reminderexample.model.dtos

import com.example.reminderexample.model.domain.Task

data class TaskDto(
    val description: String,
    val dueDate: String,
    val id: Int,
    val title: String,
)

fun TaskDto.toTask() =
    Task(
        id = this.id,
        title = this.title,
        description = this.description,
    )
