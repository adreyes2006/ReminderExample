package com.example.reminderexample.model.network

sealed class ApiResponse<out T> {
    data class Success<out T>(
        val data: T,
    ) : ApiResponse<T>()

    data class Error(
        val message: String,
        val throwable: Throwable? = null,
    ) : ApiResponse<Nothing>()

    object Loading : ApiResponse<Nothing>()
}
