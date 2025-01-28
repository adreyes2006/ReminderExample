package com.example.reminderexample.model.network

import com.example.reminderexample.model.domain.Task
import retrofit2.http.GET

interface ApiService {
    @GET("api/tasks")
    suspend fun getTasks(): List<Task>
}
