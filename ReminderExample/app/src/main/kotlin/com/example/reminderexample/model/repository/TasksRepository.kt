package com.example.reminderexample.model.repository

import com.example.reminderexample.model.domain.Task
import com.example.reminderexample.model.domain.toTaskDto
import com.example.reminderexample.model.dtos.toTask
import com.example.reminderexample.model.network.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class TasksRepository(
    private val retrofit: RetrofitInstance,
) {
    suspend fun getTasks(): Result<List<Task>> =
        try {
            val response = retrofit.api.getTAllTasks()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val mappedList = body.map { it.toTask() }
                Result.success(mappedList)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown Api Error"
                Result.failure(Exception("API Error: ${response.code()} - $errorMessage"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network Error: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Http Error: ${e.code()} ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(
                Exception(
                    "Unexpected Error: ${e.message}",
                    e,
                ),
            )
        }

    suspend fun addTask(task: Task): Result<Task> =
        try {
            val response = retrofit.api.addTask(task.toTaskDto())
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body.toTask())
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown Api Error"
                Result.failure(Exception("Error Adding task: ${response.code()} - $errorMessage"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network Error: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Http Exception: ${e.code()} ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected Error: ${e.message}", e))
        }

    suspend fun deleteTaskById(id: Int): Result<Unit> =
        try {
            val response = retrofit.api.deleteTask(taskId = id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown Api Error"
                Result.failure(Exception(" Error deleting task: ${response.code()} -$errorMessage"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network Error: ${e.message}", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Http Error: ${e.code()} ${e.code()}", e))
        } catch (e: Exception) {
            Result.failure(Exception("Unexpected Error: ${e.message}", e))
        }
}
