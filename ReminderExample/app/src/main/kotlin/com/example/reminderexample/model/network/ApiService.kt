package com.example.reminderexample.model.network

import com.example.reminderexample.model.dtos.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("tasks")
    suspend fun getTAllTasks(): Response<List<TaskDto>>

    @POST("tasks")
    suspend fun addTask(
        @Body task: TaskDto,
    ): Response<TaskDto>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") taskId: Int,
    ): Response<TaskDto>
}
