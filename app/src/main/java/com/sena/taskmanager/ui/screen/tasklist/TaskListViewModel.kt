package com.sena.taskmanager.ui.screen.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.taskmanager.domain.model.Task
import com.sena.taskmanager.domain.usecase.auth.GetCurrentUserUseCase
import com.sena.taskmanager.domain.usecase.auth.LogoutUserUseCase
import com.sena.taskmanager.domain.usecase.task.DeleteTaskUseCase
import com.sena.taskmanager.domain.usecase.task.GetTasksUseCase
import com.sena.taskmanager.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListState(
    val isLoading: Boolean = false,
    val items: List<Task> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListState(isLoading = true))
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        val uid = getCurrentUserUseCase()
        if (uid == null) {
            _state.value = TaskListState(error = "User not logged in")
            return
        }
        viewModelScope.launch {
            getTasksUseCase(uid)
                .catch { t ->
                    _state.value = TaskListState(error = t.message ?: "Failed to fetch tasks")
                }
                .collect { tasks ->
                    _state.value = TaskListState(items = tasks)
                }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(completed = !task.completed, updatedAt = System.currentTimeMillis())
            updateTaskUseCase(updatedTask)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    fun logout(onLogoutDone: () -> Unit) {
        viewModelScope.launch {
            logoutUserUseCase()
            onLogoutDone()
        }
    }
}
