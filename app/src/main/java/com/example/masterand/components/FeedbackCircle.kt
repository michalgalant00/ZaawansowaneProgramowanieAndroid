package com.example.masterand.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.masterand.views.FEEDBACK_CIRCLE_SIZE
import com.example.masterand.types.FeedbackType

@Composable
fun FeedbackCircle(
    type: FeedbackType,
    modifier: Modifier = Modifier
) {
    val color = when (type) {
        FeedbackType.CORRECT_POSITION -> Color.Red
        FeedbackType.WRONG_POSITION -> Color.Yellow
        FeedbackType.NOT_IN_SEQUENCE -> Color.White
    }

    Box(
        modifier = modifier
            .size(FEEDBACK_CIRCLE_SIZE)
            .clip(CircleShape)
            .background(color)
            .border(1.dp, Color.Black, CircleShape)
    )
}