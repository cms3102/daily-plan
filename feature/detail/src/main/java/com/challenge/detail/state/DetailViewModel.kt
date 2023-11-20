package com.challenge.detail.state

import androidx.lifecycle.viewModelScope
import com.sergio.common.base.BaseViewModel
import com.sergio.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel<DetailIntent, DetailState>() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    override val state: StateFlow<DetailState>
        get() = _state

    init {

    }

    override fun updateState(reduce: () -> DetailState) {
        _state.update {
            reduce()
        }
    }

    override fun handleIntent(intent: DetailIntent) {

    }


}