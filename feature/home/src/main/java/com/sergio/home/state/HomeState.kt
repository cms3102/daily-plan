package com.sergio.home.state

import com.sergio.common.base.BaseState

sealed interface HomeState : BaseState {
    data object Loading : HomeState
    data object Success : HomeState
}