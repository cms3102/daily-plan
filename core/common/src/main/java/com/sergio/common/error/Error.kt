package com.sergio.common.error

data class NotFound(override val message: String = "Not found") : Throwable()
