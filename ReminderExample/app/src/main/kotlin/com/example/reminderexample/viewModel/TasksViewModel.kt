package com.example.reminderexample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderexample.model.domain.Task
import com.example.reminderexample.model.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TasksViewModel(
    private val repository: TasksRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Task>>> = _uiState

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.getTasks()
            result
                .onSuccess { tasks ->
                    _uiState.value = UiState.Success(tasks)
                }.onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Error Getting Tasks")
                }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            val result = repository.addTask(task)
            result
                .onSuccess { addedTask ->
                    val tasks = (_uiState.value as? UiState.Success)?.data ?: emptyList()
                    _uiState.value = UiState.Success(tasks + addedTask)
                }.onFailure { error ->
                    val tasks = (_uiState.value as? UiState.Success)?.data ?: emptyList()
                    _uiState.value = UiState.Success(tasks)
                }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            val result = repository.deleteTaskById(taskId)

            result
                .onSuccess {
                    val tasks = (_uiState.value as? UiState.Success)?.data ?: emptyList()
                    val updatedTasks = tasks.filter { it.id != taskId } // Remove from list
                    _uiState.value = UiState.Success(updatedTasks)
                }.onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Not Able to Delete task")
                }
        }
    }
}

sealed class UiState<out T> {
    data class Success<out T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val message: String,
    ) : UiState<Nothing>()

    data object Loading : UiState<Nothing>()
}
