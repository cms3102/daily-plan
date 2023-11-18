package com.sergio.home.state

import com.sergio.common.base.BaseIntent

sealed interface HomeIntent : BaseIntent {

    data object LoadAllTasks : HomeIntent

}