package com.sergio.home.state

import com.challenge.model.Task
import com.sergio.common.base.BaseState

sealed interface HomeState : BaseState {
    data object Loading : HomeState
    data class Success(val data: List<Task>) : HomeState
    data class Failure(val error: Throwable) : HomeState
}