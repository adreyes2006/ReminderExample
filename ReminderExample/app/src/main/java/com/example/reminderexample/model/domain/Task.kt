package com.example.reminderexample.model.domain

data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean,
    val description: String,
)
