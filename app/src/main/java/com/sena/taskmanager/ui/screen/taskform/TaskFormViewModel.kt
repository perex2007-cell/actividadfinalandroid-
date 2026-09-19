package com.sena.taskmanager.ui.screen.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.taskmanager.domain.model.Task
import com.sena.taskmanager.domain.model.TaskDraft
import com.sena.taskmanager.domain.usecase.auth.GetCurrentUserUseCase
import com.sena.taskmanager.domain.usecase.draft.SaveDraftUseCase
import com.sena.taskmanager.domain.usecase.task.CreateTaskUseCase
import com.sena.taskmanager.domain.usecase.task.GetTasksUseCase
import com.sena.taskmanager.domain.usecase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskFormState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val existingTask: Task? = null
)

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val saveDraftUseCase: SaveDraftUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskFormState())
    val state: StateFlow<TaskFormState> = _state.asStateFlow()

    fun loadTask(taskId: String?) {
        if (taskId == null) {
            _state.value = TaskFormState()
            return
        }
        val uid = getCurrentUserUseCase() ?: return
        viewModelScope.launch {
            _state.value = TaskFormState(isLoading = true)
            try {
                val tasks = getTasksUseCase(uid).first()
                val found = tasks.find { it.id == taskId }
                if (found != null) {
                    _state.value = TaskFormState(existingTask = found)
                } else {
                    _state.value = TaskFormState(error = "Task not found")
                }
            } catch (e: Exception) {
                _state.value = TaskFormState(error = e.message ?: "Failed to load task")
            }
        }
    }

    fun saveTask(title: String, description: String, taskId: String?) {
        if (title.isBlank()) {
            _state.value = _state.value.copy(error = "Title cannot be empty")
            return
        }
        val uid = getCurrentUserUseCase()
        if (uid == null) {
            _state.value = _state.value.copy(error = "User not logged in")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val now = System.currentTimeMillis()
            val result = if (taskId == null) {
                val newTask = Task(
                    id = "",
                    ownerId = uid,
                    title = title,
                    description = description,
                    completed = false,
                    createdAt = now,
                    updatedAt = now
                )
                createTaskUseCase(newTask)
            } else {
                val existing = _state.value.existingTask
                val updatedTask = Task(
                    id = taskId,
                    ownerId = uid,
                    title = title,
                    description = description,
                    completed = existing?.completed ?: false,
                    createdAt = existing?.createdAt ?: now,
                    updatedAt = now
                )
                updateTaskUseCase(updatedTask)
            }

            if (result.isSuccess) {
                _state.value = TaskFormState(isSuccess = true)
            } else {
                _state.value = _state.value.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Operation failed")
            }
        }
    }

    fun saveAsDraft(title: String, description: String) {
        if (title.isBlank()) {
            _state.value = _state.value.copy(error = "Title required to save draft")
            return
        }
        val uid = getCurrentUserUseCase() ?: return
        viewModelScope.launch {
            val draft = TaskDraft(
                id = 0,
                ownerId = uid,
                title = title,
                description = description,
                savedAt = System.currentTimeMillis()
            )
            saveDraftUseCase(draft)
            _state.value = TaskFormState(isSuccess = true)
        }
    }

    fun resetState() {
        _state.value = TaskFormState()
    }
}
