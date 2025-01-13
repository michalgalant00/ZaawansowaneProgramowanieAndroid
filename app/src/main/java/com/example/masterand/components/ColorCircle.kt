package com.example.masterand.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.masterand.views.MAIN_CIRCLE_SIZE
import com.example.masterand.types.GameColor

@Composable
fun ColorCircle(
    color: GameColor,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(MAIN_CIRCLE_SIZE)
            .clip(CircleShape)
            .background(color.toColor())
            .border(2.dp, Color.Black, CircleShape)
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
    )
}