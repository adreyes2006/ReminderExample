package com.example.reminderexample.model.repository

import android.util.Log
import com.example.reminderexample.model.domain.Task
import com.example.reminderexample.model.dtos.toTask
import com.example.reminderexample.model.network.ApiResponse
import com.example.reminderexample.model.network.RetrofitInstance
import java.io.IOException

class TasksRepository(
    private val retrofit: RetrofitInstance,
) {
    suspend fun getTasks(): ApiResponse<List<Task>> {
        try {
            val tasks =
                retrofit.api.getTasks().map {
                    it.toTask()
                }
            return ApiResponse.Success(tasks)
        } catch (e: IOException) {
            Log.e("TasksRepository", "Network Error: ${e.message}", e)
            return ApiResponse.Error("Unexpected error", e)
        } catch (e: Exception) {
            Log.e("TasksRepository", "Unexpected Error: ${e.message}", e)
            return ApiResponse.Error("Unexpected error", e.cause)
        }
    }
}
