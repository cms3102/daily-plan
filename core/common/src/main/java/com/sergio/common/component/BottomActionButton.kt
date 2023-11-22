package com.sergio.common.component

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.sergio.common.theme.ComponentSizeRules

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var selected by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (selected) 0.95f else 1f, label = "")
    Button(
        modifier = modifier
            .height(ComponentSizeRules.Button.bottomActionButtonHeight)
            .scale(scale)
            .pointerInput(selected) {
                awaitPointerEventScope {
                    selected = if (selected) {
                        waitForUpOrCancellation()
                        false
                    } else {
                        awaitFirstDown(false)
                        true
                    }
                }
            },
        onClick = { onClick.invoke() },
        enabled = enabled,
        shape = shape,
        colors = colors
    ) {
        content()
    }
}