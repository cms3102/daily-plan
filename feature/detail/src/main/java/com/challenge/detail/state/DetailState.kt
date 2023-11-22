package com.challenge.detail.state

import com.challenge.model.Task
import com.sergio.common.base.BaseState

sealed interface DetailState : BaseState {
    data object Loading : DetailState
    data class Success(val data: Task) : DetailState
    data class Failure(val error: Throwable) : DetailState
}