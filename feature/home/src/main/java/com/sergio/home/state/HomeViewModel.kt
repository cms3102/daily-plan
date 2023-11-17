package com.sergio.home.state

import com.sergio.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent, HomeState>() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    override val state: StateFlow<HomeState>
        get() = _state

    override fun updateState(reduce: () -> HomeState) {
        TODO("Not yet implemented")
    }

    override fun handleIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }
}