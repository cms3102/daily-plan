package com.sergio.home.state

import com.sergio.common.base.BaseState

sealed interface HomeState : BaseState {
    data object Loading : HomeState
    data class Success(val data: TaskModel) : HomeState
    data class Failure(val error: Throwable) : HomeState
}