package com.sergio.dailyplan.input

import com.sergio.common.base.BaseIntent

sealed interface MainIntent : BaseIntent {
    data object Loading : MainIntent
    data object Complete : MainIntent
}