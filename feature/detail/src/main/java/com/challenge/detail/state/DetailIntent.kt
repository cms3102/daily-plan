package com.challenge.detail.state

import com.sergio.common.base.BaseIntent

sealed interface DetailIntent : BaseIntent {

    data class LoadTask(val id: Long?) : DetailIntent

    data class CompleteTask(val id: Long) : DetailIntent

}