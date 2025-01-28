package com.example.reminderexample.model.dtos

data class TaskDto(
    val description: String,
    val dueDate: String,
    val id: Int,
    val title: String
)