package com.sergio.home.state

import androidx.lifecycle.viewModelScope
import com.challenge.model.Task
import com.challenge.model.TaskType
import com.sergio.common.base.BaseViewModel
import com.sergio.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseViewModel<HomeIntent, HomeState>() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    override val state: StateFlow<HomeState>
        get() = _state

    init {
        observeTasks()
    }

    override fun updateState(reduce: () -> HomeState) {
        _state.update {
            reduce()
        }
    }

    override fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.LoadAllTasks -> {
                loadAllTasks()
            }
            is HomeIntent.SaveTask -> {
                saveTask(
                    title = intent.title,
                    description = intent.description,
                    type = intent.type,
                    dueDate = intent.dueDate
                )
            }
        }
    }

    private fun observeTasks() {
        viewModelScope.launch {
            taskRepository.tasks
                .distinctUntilChanged()
                .map { it.toModel() }
                .flowOn(defaultDispatcher)
                .collectLatest { model ->
                    updateState {
                        HomeState.Success(model)
                    }
                }
        }
    }

    private fun loadAllTasks() {
        viewModelScope.launch {
            val taskList = taskRepository.loadAllTasks()
            val model = withContext(defaultDispatcher) { taskList.toModel() }
            updateState {
                HomeState.Success(model)
            }
        }
    }

    private fun saveTask(
        title: String,
        description: String,
        type: TaskType,
        dueDate: String
    ) {
        viewModelScope.launch {
            taskRepository.saveTask(
                Task(
                    title = title,
                    description = description,
                    type = type,
                    complete = false,
                    dueDate = dueDate
                )
            )
        }
    }

}