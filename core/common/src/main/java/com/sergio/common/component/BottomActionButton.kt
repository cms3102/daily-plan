package com.sergio.common.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.sergio.common.theme.ComponentSizeRules

@Composable
fun BottomActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier.height(ComponentSizeRules.Button.bottomActionButtonHeight),
        onClick = { onClick.invoke() },
        enabled = enabled,
        shape = shape,
        colors = colors
    ) {
        content()
    }
}