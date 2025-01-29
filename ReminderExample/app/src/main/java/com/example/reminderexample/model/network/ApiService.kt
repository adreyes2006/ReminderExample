package com.example.reminderexample.model.network

import com.example.reminderexample.model.dtos.TaskDto
import retrofit2.http.GET

interface ApiService {
    @GET("api/tasks")
    suspend fun getTasks(): List<TaskDto>
}
