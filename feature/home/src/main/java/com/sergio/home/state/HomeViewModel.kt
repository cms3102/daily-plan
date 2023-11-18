package com.sergio.home.state

import androidx.lifecycle.viewModelScope
import com.sergio.common.base.BaseViewModel
import com.sergio.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel<HomeIntent, HomeState>() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    override val state: StateFlow<HomeState>
        get() = _state

    init {
        sendIntent(HomeIntent.LoadAllTasks)
    }

    override fun updateState(reduce: () -> HomeState) {
        _state.update {
            reduce()
        }
    }

    override fun handleIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.LoadAllTasks -> {
                loadAllTasks()
            }
        }
    }

    private fun loadAllTasks() {
        viewModelScope.launch {
            val result = taskRepository.loadAllTasks()
            updateState {
                HomeState.Success(result)
            }
        }
    }

}