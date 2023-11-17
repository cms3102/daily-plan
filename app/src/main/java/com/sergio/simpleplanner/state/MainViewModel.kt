package com.sergio.simpleplanner.state

import com.sergio.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainIntent, MainState>() {

    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    override val state: StateFlow<MainState>
        get() = _state

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.Loading -> updateState { MainState.Loading }
            MainIntent.Complete -> updateState { MainState.Success }
        }
    }

    override fun updateState(reduce: () -> MainState) {
        _state.update { reduce() }
    }

}