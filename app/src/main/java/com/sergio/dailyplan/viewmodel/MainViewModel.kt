package com.sergio.dailyplan.viewmodel

import androidx.lifecycle.viewModelScope
import com.sergio.common.base.BaseViewModel
import com.sergio.dailyplan.input.MainIntent
import com.sergio.dailyplan.output.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainIntent, MainState>() {

    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    override val state: StateFlow<MainState>
        get() = _state.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MainState.Loading
        )

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