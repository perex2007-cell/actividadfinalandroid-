package com.example.actividadfinalandroid.ui.draft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividadfinalandroid.domain.model.TaskDraft
import com.example.actividadfinalandroid.domain.use_case.auth.GetCurrentUserUseCase
import com.example.actividadfinalandroid.domain.use_case.draft.DeleteDraftUseCase
import com.example.actividadfinalandroid.domain.use_case.draft.GetDraftsUseCase
import com.example.actividadfinalandroid.domain.use_case.draft.PublishDraftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DraftListState(
    val isLoading: Boolean = false,
    val items: List<TaskDraft> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class DraftListViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getDraftsUseCase: GetDraftsUseCase,
    private val publishDraftUseCase: PublishDraftUseCase,
    private val deleteDraftUseCase: DeleteDraftUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DraftListState(isLoading = true))
    val state: StateFlow<DraftListState> = _state.asStateFlow()

    init {
        loadDrafts()
    }

    fun loadDrafts() {
        val uid = getCurrentUserUseCase()
        if (uid == null) {
            _state.value = DraftListState(error = "User not logged in")
            return
        }
        viewModelScope.launch {
            getDraftsUseCase(uid)
                .catch { t ->
                    _state.value = DraftListState(error = t.message ?: "Failed to load drafts")
                }
                .collect { drafts ->
                    _state.value = DraftListState(items = drafts)
                }
        }
    }

    fun publishDraft(draft: TaskDraft) {
        viewModelScope.launch {
            val result = publishDraftUseCase(draft)
            if (result.isFailure) {
                _state.value = _state.value.copy(error = result.exceptionOrNull()?.message ?: "Publish failed")
            }
        }
    }

    fun deleteDraft(draft: TaskDraft) {
        viewModelScope.launch {
            deleteDraftUseCase(draft)
        }
    }
}
