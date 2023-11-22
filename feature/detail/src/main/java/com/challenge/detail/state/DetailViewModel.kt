package com.challenge.detail.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.challenge.detail.navigation.detailArg
import com.sergio.common.base.BaseViewModel
import com.sergio.common.error.NotFound
import com.sergio.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : BaseViewModel<DetailIntent, DetailState>() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    override val state: StateFlow<DetailState>
        get() = _state

    init {
        val taskId: Long? = savedStateHandle[detailArg]
        sendIntent(DetailIntent.LoadTask(taskId))
    }

    override fun updateState(reduce: () -> DetailState) {
        _state.update { reduce() }
    }

    override fun handleIntent(intent: DetailIntent) {
        when(intent) {
            is DetailIntent.LoadTask -> loadTask(intent.id)
            is DetailIntent.CompleteTask -> completeTask(intent.id)
        }
    }

    private fun loadTask(id: Long?) {
        viewModelScope.launch {
            if (id != null) {
                val task = taskRepository.loadTask(id)
                updateState { DetailState.Success(task) }
            } else {
                updateState { DetailState.Failure(NotFound()) }
            }
        }
    }

    private fun completeTask(id: Long) {
        viewModelScope.launch {
            taskRepository.completeTask(id)
            loadTask(id)
        }
    }


}