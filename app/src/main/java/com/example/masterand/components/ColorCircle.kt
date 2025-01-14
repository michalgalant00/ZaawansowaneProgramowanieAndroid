package com.example.masterand.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.masterand.views.MAIN_CIRCLE_SIZE
import com.example.masterand.types.GameColor
import kotlinx.coroutines.delay

@Composable
fun ColorCircle(
    color: GameColor,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    waveDelay: Int = 0,
    isWaveAnimating: Boolean = false
) {
    var lastColor by remember { mutableStateOf(color) }
    val colorChangeAnimState = remember { MutableTransitionState(false) }
    val waveAnimState = remember { MutableTransitionState(false) }

    // Trigger color change animation
    LaunchedEffect(color) {
        if (color != lastColor) {
            colorChangeAnimState.targetState = true
            lastColor = color
        }
    }

    // Reset color change animation after completion
    LaunchedEffect(colorChangeAnimState.currentState) {
        if (colorChangeAnimState.currentState) {
            colorChangeAnimState.targetState = false
        }
    }

    // Trigger wave animation
    LaunchedEffect(isWaveAnimating) {
        if (isWaveAnimating) {
            delay(waveDelay.toLong())
            waveAnimState.targetState = true
            delay(300) // Duration of wave animation
            waveAnimState.targetState = false
        }
    }

    val colorChangeTransition = updateTransition(colorChangeAnimState, label = "shake")
    val waveTransition = updateTransition(waveAnimState, label = "wave")

    val shakeOffsetY by colorChangeTransition.animateFloat(
        label = "shakeOffsetY",
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = 100,
                    easing = FastOutSlowInEasing
                )
            } else {
                tween(
                    durationMillis = 100,
                    easing = FastOutSlowInEasing
                )
            }
        }
    ) { state ->
        if (state) -5f else 0f
    }

    val waveOffsetY by waveTransition.animateFloat(
        label = "waveOffsetY",
        transitionSpec = {
            tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        }
    ) { state ->
        if (state) -15f else 0f
    }

    Box(
        modifier = modifier
            .size(MAIN_CIRCLE_SIZE)
            .offset(y = (shakeOffsetY + waveOffsetY).dp)
            .clip(CircleShape)
            .background(color.toColor())
            .border(2.dp, Color.Black, CircleShape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
    )
}