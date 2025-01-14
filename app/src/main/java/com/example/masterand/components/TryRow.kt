package com.example.masterand.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.masterand.views.MAIN_CIRCLE_SIZE
import com.example.masterand.types.FeedbackType
import com.example.masterand.types.GameColor

@Composable
fun TryRow(
    onTrySubmitted: (List<GameColor>) -> List<FeedbackType>,
    modifier: Modifier = Modifier,
) {
    var colors by remember { mutableStateOf(List(4) { GameColor.NONE }) }
    var feedback by remember { mutableStateOf<List<FeedbackType>?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var isWaveAnimating by remember { mutableStateOf(false) }

    if (showColorPicker) {
        ColorPickerModal(
            onDismiss = { showColorPicker = false },
            onColorSelected = { color ->
                val newColors = colors.toMutableList()
                newColors[selectedIndex] = color
                colors = newColors
                showColorPicker = false
            },
            usedColors = colors.toSet()
        )
    }

    Row(
        modifier = modifier
            .height(64.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Main circles
        colors.forEachIndexed { index, currentColor ->
            ColorCircle(
                color = currentColor,
                enabled = !isSubmitted,
                onClick = {
                    if (!isSubmitted) {
                        selectedIndex = index
                        showColorPicker = true
                    }
                },
                waveDelay = index * 100, // Delay each circle's wave by 100ms
                isWaveAnimating = isWaveAnimating
            )
        }

        // Confirm button
        if ((colors.none { it == GameColor.NONE } && !isSubmitted) || isSubmitted) {
            ConfirmButton(
                modifier = Modifier,
                onClick = {
                    isWaveAnimating = true
                    feedback = onTrySubmitted(colors)
                    isSubmitted = true
                }
            )
        } else {
            Spacer(modifier = Modifier.width(MAIN_CIRCLE_SIZE))
        }

        // Feedback circles
        if (isSubmitted && feedback != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FeedbackCircle(type = feedback!![0])
                    FeedbackCircle(type = feedback!![1])
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FeedbackCircle(type = feedback!![2])
                    FeedbackCircle(type = feedback!![3])
                }
            }
        } else {
            Spacer(modifier = Modifier
                .width(MAIN_CIRCLE_SIZE - 2.dp)
                .height(MAIN_CIRCLE_SIZE))
        }
    }
}