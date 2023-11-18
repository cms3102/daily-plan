package com.sergio.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseViewModel<I : BaseIntent, S : BaseState> : ViewModel() {

    private val _intentHandlerFlow: MutableSharedFlow<I> = MutableSharedFlow()
    private val intentHandlerJob = _intentHandlerFlow
        .distinctUntilChanged()
        .process()
        .launchIn(viewModelScope)

    abstract val state: StateFlow<S>

    fun sendIntent(intent: I) {
        viewModelScope.launch {
            _intentHandlerFlow.emit(intent)
        }
    }

    private fun Flow<I>.process() = onEach { intent ->
        handleIntent(intent)
    }
    protected abstract fun handleIntent(intent: I)
    protected abstract fun updateState(reduce: () -> S)

}