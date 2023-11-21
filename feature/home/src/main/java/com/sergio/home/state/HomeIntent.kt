package com.sergio.home.state

import com.challenge.model.TaskType
import com.sergio.common.base.BaseIntent

sealed interface HomeIntent : BaseIntent {

    data object LoadAllTasks : HomeIntent

    data class SaveTask(
        val title: String,
        val description: String,
        val type: TaskType
    ) : HomeIntent

}