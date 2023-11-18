package com.sergio.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object ShapeRules {

    val roundedCornerShape = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(12.dp),
    )

}

object ElevationRules {

    @Composable
    fun cardElevation() = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )

}